package bushigen.nongo.security;

import java.util.List;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import bushigen.nongo.dao.generated.UsersMapper;
import bushigen.nongo.model.Users;
import bushigen.nongo.model.UsersExample;
import lombok.RequiredArgsConstructor;

/**
 * ユーザ名を受け取り、LoginUserDetailsオブジェクトに変換
 */
@Service
@RequiredArgsConstructor
public class LoginUserDetailsService implements UserDetailsService{
    private final UsersMapper usersMapper;

    @Override
    public LoginUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsersExample example = new UsersExample();
        example.createCriteria().andUserNameEqualTo(username);
        List<Users> users = usersMapper.selectByExample(example);
        if (users.isEmpty()) {
            throw new UsernameNotFoundException("Please try again");
        }

        Users user = users.get(0);
        return new LoginUserDetails(user);
    }
}
