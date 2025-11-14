/**
 * OpenAPI生成APIメソッドを安全に呼び出すための汎用ヘルパー
 * エラーハンドリングと成功ロジックを一貫して保ちます
 */

/**
 * GETリクエスト用
 * OpenAPI-CLIで作成したクラスのメソッドを実行
 */
export async function handleApiGet<TReq, TRes>(
  apiMethod: (req: TReq) => Promise<TRes>,
  requestData: TReq,
  onSuccess: (res: TRes) => void | Promise<void>,
  onError?: (err: unknown) => void | Promise<void>
) {
  try {
    const result = await apiMethod(requestData);
    await onSuccess(result);
  } catch (err: unknown) {
    console.error('handleApiGetでAPIエラーをキャッチ:', err);

    if (!onError) return;

    // errがresponseプロパティを持っている場合、エラーオブジェクト抽出
    const response = err?.response as Response | undefined;
    if (response instanceof Response) {
      try {
        const contentType = response.headers.get('content-type');
        if (contentType && contentType.includes('application/json')) {
          const data = await response.json();
          await onError(data);
        } else {
          // プレーンテキストレスポンスを処理（例: 403エラー）
          const text = await response.text();
          await onError({ general: text || `サーバーエラー (${response.status})` });
        }
      } catch {
        await onError({ general: `サーバーエラー (${response.status})` });
      }
      return;
    }

    if (typeof err === 'object' && err !== null) {
      await onError(err);
      return;
    }

    await onError({ general: '予期せぬエラーが発生しました。' });
  }
}

/**
 * POSTリクエスト用
 * OpenAPI-CLIで作成したクラスのメソッドを実行
 */
export async function handleApiPost<TReq, TRes>(
  apiMethod: (req: TReq) => Promise<TRes>,
  requestData: TReq,
  onSuccess: (res: TRes) => void | Promise<void>,
  onError?: (err: unknown) => void | Promise<void>
) {
  try {
    const result = await apiMethod(requestData);
    await onSuccess(result);
  } catch (err: unknown) {
    console.error('handleApiPostでAPIエラーをキャッチ:', err);

    if (!onError) return;

    // errがresponseプロパティを持っている場合、エラーオブジェクト抽出
    const response = err?.response as Response | undefined;
    if (response instanceof Response) {
      try {
        const contentType = response.headers.get('content-type');
        if (contentType && contentType.includes('application/json')) {
          const data = await response.json();
          await onError(data);
        } else {
          // プレーンテキストレスポンスを処理（例: 403エラー）
          const text = await response.text();
          await onError({ general: text || `サーバーエラー (${response.status})` });
        }
      } catch {
        await onError({ general: `サーバーエラー (${response.status})` });
      }
      return;
    }

    // 不明なレスポンス内容
    if (typeof err === 'object' && err !== null) {
      await onError(err);
      return;
    }

    // フォールバック
    await onError({ general: '予期せぬエラーが発生しました。' });
  }
}
