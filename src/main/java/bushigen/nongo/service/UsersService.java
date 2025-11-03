package bushigen.nongo.service;

import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import bushigen.nongo.dao.generated.UsersMapper;
import bushigen.nongo.global.BusinessException;
import bushigen.nongo.model.Users;
import bushigen.nongo.model.UsersExample;

/**
 * Service class for managing Users entities.
 * This service provides business logic for user operations,
 * acting as an intermediary between the controller and repository layers.
 */
@Service
public class UsersService {

  private final UsersMapper usersMapper;
  private final PasswordEncoder passwordEncoder;

  /**
   * Constructor
   */
  public UsersService(UsersMapper usersMapper, PasswordEncoder passwordEncoder) {
    this.usersMapper = usersMapper;
    this.passwordEncoder = passwordEncoder;
  }

  /**
   * 新規ユーザー登録
   */
  public void signup(String userName, String email, String password) {
    // ユーザ名がすでに存在するかチェック
    UsersExample example = new UsersExample();
    example.createCriteria().andUserNameEqualTo(userName);
    List<Users> existingUsers = usersMapper.selectByExample(example);
    if (!existingUsers.isEmpty()) {
      throw new BusinessException("ユーザ名がすでに存在します");
    }

    // メールアドレスがすでに存在するかチェック
    UsersExample emailExample = new UsersExample();
    emailExample.createCriteria().andEmailEqualTo(email);
    List<Users> existingEmails = usersMapper.selectByExample(emailExample);
    if (!existingEmails.isEmpty()) {
      throw new BusinessException("メールアドレスがすでに存在します");
    }

    // 新規ユーザー登録
    Users user = new Users();
    user.setUserName(userName);
    user.setEmail(email);
    user.setPassword(passwordEncoder.encode(password));

    usersMapper.insertSelective(user);
  }
}
