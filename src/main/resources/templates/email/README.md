# Email Template System

このディレクトリには、nongoアプリケーションで使用するメールテンプレートが含まれています。

## 📁 ディレクトリ構成

```
templates/email/
├── base-email.html                    # ベーステンプレート（すべてのメールで共有）
├── content/                           # メールコンテンツテンプレート
│   ├── verification-email-content.html  # 認証メールのコンテンツ
│   └── example-welcome-email-content.html  # 例：ウェルカムメール
└── README.md                          # このファイル
```

## 🎨 スタイル共有システム

すべてのメールは `base-email.html` をベースに構築されます。これにより：

- ✅ **一貫性**: すべてのメールで同じデザイン
- ✅ **メンテナンス性**: 1箇所の変更ですべてのメールに反映
- ✅ **拡張性**: 新しいメールタイプを簡単に追加

## 🚀 新しいメールタイプの作成方法

### 1. コンテンツテンプレートを作成

`content/` ディレクトリに新しいHTMLファイルを作成：

```html
<!-- content/your-email-content.html -->
<h2 class="title">メールタイトル</h2>

<p class="text">{{userName}} 様</p>

<p class="text">メール本文...</p>

<!-- ボタン -->
<table role="presentation" class="button-wrapper">
  <tr>
    <td align="center" class="button-cell">
      <a href="{{actionUrl}}" class="button">アクションボタン</a>
    </td>
  </tr>
</table>
```

### 2. EmailServiceにメソッドを追加

```java
public void sendYourEmail(String email, String userName, String actionUrl) {
  try {
    String htmlContent = buildYourEmailHtml(userName, actionUrl);

    MimeMessage mimeMessage = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

    helper.setTo(email);
    helper.setSubject("【nongo】メール件名");
    helper.setText(htmlContent, true);

    mailSender.send(mimeMessage);
    log.info("Your email sent to: {}", email);
  } catch (MessagingException e) {
    log.error("Failed to send your email to: {}", email, e);
    throw new RuntimeException("メール送信に失敗しました", e);
  }
}

private String buildYourEmailHtml(String userName, String actionUrl) {
  Map<String, String> replacements = new HashMap<>();
  replacements.put("userName", userName);
  replacements.put("actionUrl", actionUrl);

  return composeEmail(
    "templates/email/content/your-email-content.html",
    "メールタイトル",
    replacements
  );
}
```

## 🎨 利用可能なCSSクラス

### レイアウト
- `.wrapper` - メール全体のラッパー
- `.container` - メインコンテナ
- `.header` - ヘッダー部分
- `.body` - 本文部分
- `.footer` - フッター部分

### テキスト
- `.title` - メインタイトル（h2）
- `.text` - 通常のテキスト（margin-bottom: 20px）
- `.text-last` - 最後のテキスト（margin-bottom: 30px）
- `.note-text` - 注釈テキスト（小さめ・グレー）

### ボタン
- `.button-wrapper` - ボタン用テーブル
- `.button-cell` - ボタンセル
- `.button` - ボタンリンク（グラデーション背景）

### インフォボックス
- `.info-box` - 情報ボックス（青）
  - `.info-text` - ボックス内テキスト
- `.warning-box` - 警告ボックス（黄色）
  - `.warning-text` - ボックス内テキスト
- `.success-box` - 成功ボックス（緑）
  - `.success-text` - ボックス内テキスト

### その他
- `.help-text` - ヘルプテキスト（上部ボーダー付き）
- `.url-box` - URL表示ボックス（青背景・左ボーダー）

## 📝 プレースホルダー

テンプレート内で `{{変数名}}` の形式で変数を使用できます：

```html
<p class="text">{{userName}} 様</p>
<a href="{{verificationUrl}}" class="button">認証する</a>
```

Java側で置換：

```java
Map<String, String> replacements = new HashMap<>();
replacements.put("userName", "山田太郎");
replacements.put("verificationUrl", "https://example.com/verify");
```

## 💡 ヒント

1. **デザインの統一**: 新しいスタイルが必要な場合は、`base-email.html` に追加してください
2. **テストメール**: 実装前に、HTMLファイルをブラウザで開いて確認できます
3. **メールクライアント互換性**: テーブルレイアウトを使用しているため、すべてのメールクライアントで動作します

## 🔧 メンテナンス

### ベーステンプレートの更新

`base-email.html` を変更すると、すべてのメールに反映されます：

- ヘッダー/フッターのデザイン変更
- 新しいCSSクラスの追加
- グローバルなスタイルの調整

### 既存メールの更新

1. `content/` ディレクトリの該当ファイルを編集
2. 必要に応じて `EmailService` のメソッドを更新
3. 変更をテスト

## 📚 参考

- ベーステンプレート: `base-email.html`
- 実装例: `content/verification-email-content.html`
- サンプル: `content/example-welcome-email-content.html`
