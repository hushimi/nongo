package bushigen.nongo.security;

import java.io.IOException;
import java.util.ArrayList;
import jakarta.servlet.http.Cookie;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthorizeFilter extends OncePerRequestFilter{
  private final JwtUtil jwtUtil;
  private final AntPathMatcher pathMatcher = new AntPathMatcher();

  // Authentication不要パス
  private static final String[] PERMITTED_PATHS = {
    "/",
    "/index.html",
    "/favicon.ico",
    "/_app/**",
    "/login",
    "/signup",
    "/logout",
    "/is-token-valid",
    "/api-docs*/**",
    "/swagger-ui/**"
  };

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain
  ) throws ServletException, IOException {
    String servletPath = request.getServletPath();

    // 認証不要パスの場合は認証をスキップ
    boolean isPermitted = false;
    for (String permittedPath : PERMITTED_PATHS) {
      if (pathMatcher.match(permittedPath, servletPath)) {
        isPermitted = true;
        break;
      }
    }

    // 認証不要パス以外の場合は認証を実行
    if (isPermitted) {
      filterChain.doFilter(request, response);
      return;
    }

    String token = null;

    // まずCookieからJWTトークンを取得
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
      filterChain.doFilter(request, response);
      return;
    }

    try {
      DecodedJWT decoded = jwtUtil.verify(token);
      String username = decoded.getClaim("username").asString();
      // 認証を設定(ロールは含まれない)
      UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
        username, null, new ArrayList<>()
      );
      SecurityContextHolder.getContext().setAuthentication(auth);
    } catch (Exception e) {
      SecurityContextHolder.clearContext();
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }

    filterChain.doFilter(request, response);
  }
}
