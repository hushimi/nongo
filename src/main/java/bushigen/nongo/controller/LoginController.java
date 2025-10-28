package bushigen.nongo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import bushigen.nongo.dto.request.LoginRequest;
import bushigen.nongo.security.JwtUtil;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class LoginController {
    private final AuthenticationProvider authenticationProvider;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            authenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.username(), request.password()
                )
            );

            String token = jwtUtil.createToken(request.username());
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + token); // exposed by CORS config
            return ResponseEntity.ok().headers(headers).body(token); // return token in body as well
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
    }
}
