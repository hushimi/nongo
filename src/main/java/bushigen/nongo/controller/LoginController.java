package bushigen.nongo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import bushigen.nongo.dto.request.LoginRequest;
import bushigen.nongo.dto.request.SignupRequest;
import bushigen.nongo.security.JwtUtil;
import bushigen.nongo.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controller class for managing user authentication and registration.
 * This controller handles user authentication and registration endpoints.
 */
@Slf4j
@RestController
@CrossOrigin
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "User authentication and registration API")
public class LoginController {
  private final AuthenticationManager authenticationManager;
  private final JwtUtil jwtUtil;
  private final UsersService usersService;

  /**
   * ログインAPI
   * JWTトークンをHTTP-only Cookieに設定
   */
  @Operation(
    summary = "User login",
    description = "Authenticate user and return JWT token in HTTP-only cookie"
  )
  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequest request, jakarta.servlet.http.HttpServletResponse response) {
    try {
      log.info(request.toString());
      // 認証実行
      authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
          request.user_name(), request.password()
        )
      );

      // ユーザ名を元にJWTトークン作成
      String token = jwtUtil.createToken(request.user_name());

      // JWTトークンをHTTP-only Cookieに設定
      jakarta.servlet.http.Cookie cookie = new jakarta.servlet.http.Cookie("JWT_TOKEN", token);
      cookie.setHttpOnly(true);
      cookie.setSecure(false); // HTTPS環境ではtrueに設定
      cookie.setPath("/");
      cookie.setMaxAge((int) (7 * 24 * 60 * 60)); // 7日間有効
      response.addCookie(cookie);

      return ResponseEntity.ok().body("Login successful");
    } catch (AuthenticationException e) {
      return ResponseEntity.status(401).body("Unauthorized");
    }
  }

  /**
   * サインアップAPI
   */
  @Operation(
    summary = "User signup",
    description = "Register a new user account"
  )
  @PostMapping("/signup")
  public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest request) {
    usersService.signup(
      request.user_name(),
      request.email(),
      request.password()
    );
    return ResponseEntity.ok().body("User registered successfully");
  }
}
