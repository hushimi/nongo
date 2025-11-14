import { redirect } from '@sveltejs/kit';
import type { PageLoad } from './$types';

export const load: PageLoad = async ({ url, fetch }) => {
  const token = url.searchParams.get('token');

  if (!token) {
    throw redirect(302, '/error?type=verification&message=' + encodeURIComponent('認証トークンが指定されていません'));
  }

  try {
    // 認証API呼び出し
    const apiUrl = `/api/verify-email?token=${encodeURIComponent(token)}`;
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
    // SvelteKit の redirect は特殊なオブジェクトなので、そのまま再スロー
    // redirect() が投げるエラーには特定のシンボルが含まれている
    if (err && typeof err === 'object' && 'status' in err && 'location' in err) {
      throw err;
    }
    // それ以外のエラー（ネットワークエラーなど）の場合はエラーページへリダイレクト
    console.error('認証エラー:', err);
    throw redirect(302, '/error?type=verification&message=' + encodeURIComponent('認証処理中にエラーが発生しました'));
  }
};
