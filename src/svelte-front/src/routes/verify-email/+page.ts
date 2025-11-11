import { redirect } from '@sveltejs/kit';
import type { PageLoad } from './$types';

export const load: PageLoad = async ({ url, fetch }) => {
  const token = url.searchParams.get('token');

  if (!token) {
    throw redirect(302, '/error?type=verification&message=' + encodeURIComponent('認証トークンが指定されていません'));
  }

  try {
    // 認証API呼び出し
    const apiUrl = `/verify-email?token=${encodeURIComponent(token)}`;
    const response = await fetch(apiUrl, {
      method: 'GET',
      headers: {
        'Accept': 'text/plain',
      },
    });

    if (response.ok) {
      // 認証成功 - ログインページへリダイレクト
      throw redirect(302, '/login?verified=true');
    } else {
      // 認証失敗 - エラーページへリダイレクト
      const errorMessage = await response.text();
      throw redirect(302, '/error?type=verification&message=' + encodeURIComponent(errorMessage || '認証に失敗しました'));
    }
  } catch (err) {
    // リダイレクトエラーの場合は再スロー
    if (err instanceof Response && err.status >= 300 && err.status < 400) {
      throw err;
    }
    // それ以外のエラーの場合はエラーページへリダイレクト
    throw redirect(302, '/error?type=verification&message=' + encodeURIComponent('認証処理中にエラーが発生しました'));
  }
};
