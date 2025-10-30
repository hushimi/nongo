package bushigen.nongo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import bushigen.nongo.dto.request.LoginRequest;
import bushigen.nongo.security.JwtUtil;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class LoginController {
  private final AuthenticationManager authenticationManager;
  private final JwtUtil jwtUtil;

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequest request) {
    try {
      // 認証実行
      authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
          request.username(), request.password()
        )
      );

      // ユーザ名を元にトークン作成
      // トークンをヘッダーとボディに含める
      String token = jwtUtil.createToken(request.username());
      HttpHeaders headers = new HttpHeaders();
      headers.add("Authorization", "Bearer " + token);
      return ResponseEntity.ok().headers(headers).body(token);
    } catch (AuthenticationException e) {
      return ResponseEntity.status(401).body("Unauthorized");
    }
  }
}
