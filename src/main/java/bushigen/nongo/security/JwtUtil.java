package bushigen.nongo.security;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;

@Component
public class JwtUtil {
  private final Algorithm algorithm;
  private final long expireSeconds;

  /**
   * application.propertiesを元にsecretと有効期間を設定
   * @param secret
   * @param expireSeconds
   */
  public JwtUtil(
    @Value("${app.jwt.secret}") String secret,
    @Value("${app.jwt.expire-seconds}") long expireSeconds
  ) {
    this.algorithm = Algorithm.HMAC256(secret);
    this.expireSeconds = expireSeconds;
  }

  public String createToken(String username) {
    Date now = new Date();
    Date exp = new Date(now.getTime() + expireSeconds * 1000);
    return JWT.create()
      .withClaim("username", username)
      .withIssuedAt(now)
      .withExpiresAt(exp)
      .sign(algorithm);
  }

  public DecodedJWT verify(String token) {
    return JWT.require(algorithm).build().verify(token);
  }
}
