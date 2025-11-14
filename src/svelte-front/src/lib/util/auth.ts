/**
 * 認証ユーティリティ関数
 * JWTトークンはHTTP-only Cookieに保存されているため、
 * is-token-validエンドポイントにリクエストを送信して認証を確認します
 */

import { AuthenticationApi } from '$lib/types';
import { apiConfig } from '$lib/config/api';

let isAuthenticatedCache: boolean | null = null;
const api = new AuthenticationApi(apiConfig);

/**
 * is-token-valid APIを使用してユーザーが認証されているかをチェック
 * 認証されている場合はtrueを、そうでない場合はfalseを返します
 */
export async function checkAuthentication(): Promise<boolean> {
  if (isAuthenticatedCache !== null) {
    return isAuthenticatedCache;
  }

  try {
    const response = await api.isTokenValidRaw({});
    const data = await response.value();
    const authenticated = data.valid === true;
    isAuthenticatedCache = authenticated;
    return authenticated;
  } catch {
    isAuthenticatedCache = false;
    return false;
  }
}

/**
 * 認証キャッシュをクリア（ログアウトまたはログイン後に呼び出し）
 */
export function clearAuthCache(): void {
  isAuthenticatedCache = null;
}
