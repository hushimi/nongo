package bushigen.nongo.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import bushigen.nongo.dao.generated.EmailVerificationTokensMapper;
import bushigen.nongo.dao.generated.UsersMapper;
import bushigen.nongo.global.BusinessException;
import bushigen.nongo.model.EmailVerificationTokens;
import bushigen.nongo.model.EmailVerificationTokensExample;
import bushigen.nongo.model.Users;
import bushigen.nongo.model.UsersExample;

/**
 * ユーザーエンティティを管理するサービスクラス
 * ユーザー操作のビジネスロジックを提供し、
 * コントローラーとリポジトリ層の間の仲介役を果たします
 */
@Service
public class UsersService {

  private final UsersMapper usersMapper;
  private final PasswordEncoder passwordEncoder;
  private final EmailVerificationTokensMapper emailVerificationTokensMapper;
  private final EmailService emailService;

  /**
   * コンストラクタ
   */
  public UsersService(
    UsersMapper usersMapper,
    PasswordEncoder passwordEncoder,
    EmailVerificationTokensMapper emailVerificationTokensMapper,
    EmailService emailService
  ) {
    this.usersMapper = usersMapper;
    this.passwordEncoder = passwordEncoder;
    this.emailVerificationTokensMapper = emailVerificationTokensMapper;
    this.emailService = emailService;
  }

  /**
   * 新規ユーザー登録
   */
  @Transactional
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
    user.setVerified(false); // 認証前はfalse

    usersMapper.insertSelective(user);

    // メール認証トークン作成
    String token = UUID.randomUUID().toString();
    Date expiresAt = Date.from(Instant.now().plus(24, ChronoUnit.HOURS));

    // 既存のアクティブな認証トークンレコードを無効化（1ユーザー1アクティブトークンの制約）
    EmailVerificationTokensExample tokenExample = new EmailVerificationTokensExample();
    tokenExample.createCriteria()
      .andUserIdEqualTo(user.getId())
      .andIsActiveEqualTo(true);
    List<EmailVerificationTokens> existingTokens = emailVerificationTokensMapper.selectByExample(tokenExample);
    for (EmailVerificationTokens existingToken : existingTokens) {
      existingToken.setIsActive(false);
      emailVerificationTokensMapper.updateByPrimaryKeySelective(existingToken);
    }

    // 新しい認証トークンレコードを作成
    EmailVerificationTokens verificationToken = new EmailVerificationTokens();
    verificationToken.setUserId(user.getId());
    verificationToken.setToken(token);
    verificationToken.setExpiresAt(expiresAt);
    verificationToken.setIsActive(true);
    emailVerificationTokensMapper.insertSelective(verificationToken);

    // 認証メール送信
    emailService.sendVerificationEmail(email, userName, token);
  }

  /**
   * メール認証トークンでアカウントを認証
   * @param token 認証トークン
   * @throws BusinessException トークンが無効または期限切れの場合
   */
  @Transactional
  public void verifyEmail(String token) {
    // トークンを検索
    EmailVerificationTokensExample tokenExample = new EmailVerificationTokensExample();
    tokenExample.createCriteria()
      .andTokenEqualTo(token)
      .andIsActiveEqualTo(true);
    List<EmailVerificationTokens> tokens = emailVerificationTokensMapper.selectByExample(tokenExample);

    if (tokens.isEmpty()) {
      throw new BusinessException("認証トークンが無効です");
    }

    EmailVerificationTokens verificationToken = tokens.get(0);

    // トークンの有効期限をチェック
    Date now = new Date();
    if (verificationToken.getExpiresAt().before(now)) {
      throw new BusinessException("認証トークンの有効期限が切れています");
    }

    // ユーザーを取得して認証済みに設定
    Users user = usersMapper.selectByPrimaryKey(verificationToken.getUserId());
    if (user == null) {
      throw new BusinessException("ユーザーが見つかりません");
    }

    // ユーザーを認証済みに設定
    user.setVerified(true);
    usersMapper.updateByPrimaryKeySelective(user);

    // トークンを無効化
    verificationToken.setIsActive(false);
    emailVerificationTokensMapper.updateByPrimaryKeySelective(verificationToken);
  }

  /**
   * ユーザー名でユーザーを取得
   * @param userName ユーザー名
   * @return Users ユーザー情報
   * @throws BusinessException ユーザーが見つからない場合
   */
  public Users getUserByUserName(String userName) {
    UsersExample example = new UsersExample();
    example.createCriteria().andUserNameEqualTo(userName);
    List<Users> users = usersMapper.selectByExample(example);
    if (users.isEmpty()) {
      throw new BusinessException("ユーザーが見つかりません");
    }
    return users.get(0);
  }
}
