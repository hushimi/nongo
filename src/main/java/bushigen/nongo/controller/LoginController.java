package bushigen.nongo.controller;

import jakarta.validation.Valid;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import bushigen.nongo.dto.request.LoginRequest;
import bushigen.nongo.dto.request.SignupRequest;
import bushigen.nongo.global.BusinessException;
import bushigen.nongo.model.Users;
import bushigen.nongo.security.JwtUtil;
import bushigen.nongo.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * ユーザー認証と登録を管理するコントローラークラス
 * ユーザー認証と登録のエンドポイントを処理します
 */
@Slf4j
@RestController
@RequestMapping("/api")
@CrossOrigin
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "User authentication and registration API")
public class LoginController {
  private final AuthenticationManager authenticationManager;
  private final JwtUtil jwtUtil;
  private final UsersService usersService;

  /**
   * サインアップAPI
   */
  @Operation(summary = "User signup", description = "Register a new user account")
  @PostMapping("/signup")
  public ResponseEntity<?> signup(
      @Valid @RequestBody
      SignupRequest request
  ) {
    usersService.signup(
      request.userName(),
      request.email(),
      request.password()
    );
    return ResponseEntity.ok().body("User registered successfully");
  }

  /**
   * メール認証API
   * トークンでアカウントを認証
   * フロントエンドがリダイレクトを処理するため、成功/失敗のステータスコードを返す
   */
  @Operation(summary = "Verify email", description = "Verify user account using email verification token")
  @GetMapping("/verify-email")
  public ResponseEntity<?> verifyEmail(@RequestParam String token) {
    try {
      usersService.verifyEmail(token);
      // 認証成功 - 200 OK を返す（フロントエンドがログインページへリダイレクト）
      return ResponseEntity.ok().body("アカウントが認証されました");
    } catch (BusinessException e) {
      // 認証失敗 - 400 Bad Request を返す（フロントエンドがエラーページへリダイレクト）
      return ResponseEntity.status(400).body(e.getMessage());
    } catch (Exception e) {
      log.error("Email verification error", e);
      // システムエラー - 500 Internal Server Error を返す
      return ResponseEntity.status(500).body("認証処理中にエラーが発生しました");
    }
  }

  /**
   * ログインAPI
   * JWTトークンをHTTP-only Cookieに設定
   */
  @Operation(summary = "User login", description = "Authenticate user and return JWT token in HTTP-only cookie")
  @PostMapping("/login")
  public ResponseEntity<?> login(
      @RequestBody LoginRequest request,
      @Valid HttpServletResponse response) {
    try {
      // アカウントがverifiedかチェック
      try {
        Users user = usersService.getUserByUserName(request.userName());
        if (user.getVerified() == null || !user.getVerified()) {
          return ResponseEntity.status(403).body("アカウントが認証されていません。メール認証を完了してください。");
        }
      } catch (BusinessException e) {
        // ユーザーが見つからない場合は認証を試行（認証マネージャーが適切に処理）
        log.debug("User not found during verification check: {}", request.userName());
      }

      // 認証実行
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              request.userName(), request.password()));

      // ユーザ名を元にJWTトークン作成
      String token = jwtUtil.createToken(request.userName());

      // JWTトークンをHTTP-only Cookieに設定
      Cookie cookie = new Cookie("JWT_TOKEN", token);
      cookie.setHttpOnly(true);
      cookie.setSecure(false); // HTTPS環境ではtrueに設定
      cookie.setPath("/");
      cookie.setMaxAge((int) (7 * 24 * 60 * 60)); // 7日間有効
      response.addCookie(cookie);

      return ResponseEntity.ok().body("Login successful");
    } catch (AuthenticationException e) {
      return ResponseEntity.status(401).body("Unauthorized");
    } catch (Exception e) {
      log.error("Login error", e);
      return ResponseEntity.status(500).body("Internal server error");
    }
  }

  /**
   * トークン有効性チェックAPI
   * トークンが有効かどうかを返す（401エラーは返さない）
   */
  @Operation(summary = "Check token validity", description = "Check if the JWT token is valid without returning 401 error")
  @PostMapping("/is-token-valid")
  public ResponseEntity<?> isTokenValid(HttpServletRequest request) {
    try {
      String token = null;

      // CookieからJWTトークンを取得
      Cookie[] cookies = request.getCookies();
      if (cookies != null) {
        for (Cookie cookie : cookies) {
          if ("JWT_TOKEN".equals(cookie.getName())) {
            token = cookie.getValue();
            break;
          }
        }
      }

      // Cookieにない場合はAuthorizationヘッダーから取得
      if (token == null) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
          token = authHeader.substring(7);
        }
      }

      if (token == null) {
        return ResponseEntity.ok().body(Map.of("valid", false));
      }

      // トークンを検証
      jwtUtil.verify(token);
      return ResponseEntity.ok().body(Map.of("valid", true));
    } catch (Exception e) {
      return ResponseEntity.ok().body(Map.of("valid", false));
    }
  }

  /**
   * ログアウトAPI
   * JWTトークンCookieを削除
   */
  @Operation(summary = "User logout", description = "Logout user by clearing JWT token cookie")
  @PostMapping("/logout")
  public ResponseEntity<?> logout(HttpServletResponse response) {
    // JWTトークンCookieを削除
    Cookie cookie = new Cookie("JWT_TOKEN", "");
    cookie.setHttpOnly(true);
    cookie.setSecure(false);
    cookie.setPath("/");
    cookie.setMaxAge(0);
    response.addCookie(cookie);

    return ResponseEntity.ok().body("Logout successful");
  }
}
