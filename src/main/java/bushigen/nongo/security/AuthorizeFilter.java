package bushigen.nongo.security;

import java.io.IOException;
import java.util.ArrayList;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthorizeFilter extends OncePerRequestFilter{
  private final JwtUtil jwtUtil;
  private final String loginPath = "/login";
  private final AntPathMatcher pathMatcher = new AntPathMatcher();

  @Override
  protected void doFilterInternal(
    @NonNull HttpServletRequest request,
    @NonNull HttpServletResponse response,
    @NonNull FilterChain filterChain
  ) throws jakarta.servlet.ServletException, IOException {
    // loginPathでない場合は認証を行う
    if (!pathMatcher.match(loginPath, request.getServletPath())) {
      String token = null;

      // まずCookieからJWTトークンを取得
      jakarta.servlet.http.Cookie[] cookies = request.getCookies();
      if (cookies != null) {
        for (jakarta.servlet.http.Cookie cookie : cookies) {
          if ("JWT_TOKEN".equals(cookie.getName())) {
            token = cookie.getValue();
            break;
          }
        }
      }

      // Cookieにない場合はAuthorizationヘッダーから取得（後方互換性のため）
      if (token == null) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
          token = authHeader.substring(7);
        }
      }

      if (token == null) {
        filterChain.doFilter(request, response);
        return;
      }

      try {
        DecodedJWT decoded = jwtUtil.verify(token);
        String username = decoded.getClaim("username").asString();
        // Set authentication(no roles included here)
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
          username, null, new ArrayList<>()
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
      } catch (Exception e) {
        SecurityContextHolder.clearContext();
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return;
      }
    }

    filterChain.doFilter(request, response);
  }
}
