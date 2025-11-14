# アーキテクチャ概要

## バックエンド
- フレームワーク: Spring Boot 3.x
- レイヤー構造:
  - Controller (REST API)
  - Service (ビジネスロジック)
  - Repository (MyBatis + Dynamic SQL)
  - Entity (ドメインモデル)

### バックエンドツール
- Gradle: ビルド & 依存関係管理
- Flyway: スキーママイグレーション
- MyBatis Generator: コード生成
- MapStruct: DTOマッピング
- SpringDoc: OpenAPI自動生成
- OpenAPI Generator: TypeScriptクライアント生成

### JWT認証
- com.auth0:java-jwtで実装
- OncePerRequestFilterでトークンを検証
- シークレットはapplication.ymlに保存

---

## フロントエンド
- SvelteKit + TypeScript + TailwindCSS
- OpenAPI生成のTypeScriptクライアントを使用
- `/static` にビルドしてバックエンドから配信

## フロントエンドスタック
- フレームワーク: SvelteKit 2
- スタイリング: TailwindCSS + Daisy UI
- APIクライアント: OpenAPIから生成
- アイコン: FontAwesome (svelte-fa経由)

TypeScript APIクライアントの生成:
```bash
npm run gen-tscode
```

---

## 通信
- REST API（JSON形式）
- JWT Bearerトークンによる認証

## フォルダ構成
```text
project-root/
├── .cursor/
│   ├── rules.mdc
│   └── config.mdc
├── src/
│   ├── main/
│   │   ├── java/
│   │   ├── resources/
│   │   │   ├── static/        ← ビルド済みSvelteアプリ
│   │   │   └── db/migration/  ← Flywayスクリプト
│   └── svelte-front/
│       ├── src/
│       ├── package.json
│       └── vite.config.js
└── docs/
    ├── architecture.md
```
