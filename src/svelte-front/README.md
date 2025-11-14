# SvelteKit フロントエンド

Svelteプロジェクトのビルドに必要なすべてが揃っています。[`sv`](https://github.com/sveltejs/cli)で構築されています。

## プロジェクトの作成

これを見ているということは、おそらくこのステップは完了していますね。おめでとうございます！

```sh
# 現在のディレクトリに新しいプロジェクトを作成
npx sv create

# my-appディレクトリに新しいプロジェクトを作成
npx sv create my-app
```

## 開発

プロジェクトを作成し、`npm install`（または `pnpm install`、`yarn`）で依存関係をインストールしたら、開発サーバーを起動します：

```sh
npm run dev

# または、サーバーを起動して新しいブラウザタブでアプリを開く
npm run dev -- --open
```

## ビルド

アプリの本番バージョンを作成するには：

```sh
npm run build
```

本番ビルドのプレビューは `npm run preview` で確認できます。

> アプリをデプロイするには、ターゲット環境用の[アダプター](https://svelte.dev/docs/kit/adapters)をインストールする必要がある場合があります。
