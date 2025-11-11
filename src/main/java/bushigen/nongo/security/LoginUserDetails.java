package bushigen.nongo.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import bushigen.nongo.model.Users;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * ユーザーの認証情報を保持するために使用
 */
@Getter
@RequiredArgsConstructor
public class LoginUserDetails implements UserDetails{
  private final Users user;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of();
  }

  @Override public String getPassword(){ return user.getPassword(); }
  @Override public String getUsername(){ return user.getUserName(); }
  @Override public boolean isAccountNonExpired(){ return true; }
  @Override public boolean isAccountNonLocked(){ return true; }
  @Override public boolean isCredentialsNonExpired(){ return true; }
  @Override public boolean isEnabled(){
    // アカウントが認証済みの場合のみ有効
    return user.getVerified() != null && user.getVerified();
  }
}
