/**
 * GETリクエスト送信
 * @template T - レスポンス時のデータ型
 * @param {string} url
 * @param {Record<string, any>|undefined} params - URLパラメータ
 * @param {(data: T) => void} success - 成功時に実行される関数
 * @param {(error: any) => void} failed - 失敗時に実行される関数
 * @return {Promise<void>}
 */
export async function getRequest<T>(
    url: string,
    params: Record<string, unknown> | undefined,
    success: (data: T) => void,
    failed: (error: unknown) => void
): Promise<void> {
    try {
        // URLパラメータを作成
        const query = params
            ? `?${new URLSearchParams(params as Record<string, string>).toString()}`
            : '';

        // リクエスト送信・レスポンス取得
        const response = await fetch(`${url}${query}`, {
            method: 'GET',
            headers: { 'Content-Type': 'application/json' },
        });

        // データ変換
        const contentType = response.headers.get('content-type');
        const isJson = contentType?.includes('application/json');
        const data = isJson ? await response.json() : await response.text();

        // callback実行
        if (!response.ok) {
            failed(data);
        } else {
            success(data as T);
        }
    } catch (error) {
        failed(error);
    }
}

/**
 * POSTリクエスト送信
 * @template T - レスポンスデータの型
 * @param {string} url
 * @param {any} body - リクエストボディ
 * @param {(data: T) => void} success - 成功時callback
 * @param {(error: any) => void} failed - 失敗時callback
 * @return {Promise<void>}
 */
export async function postRequest<T>(
    url: string,
    body: unknown,
    success: (data: T) => void,
    failed: (error: unknown) => void
): Promise<void> {
    try {
        // リクエスト送信・レスポンス取得
        const response = await fetch(url, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(body),
        });

        // データ変換
        const contentType = response.headers.get('content-type');
        const isJson = contentType?.includes('application/json');
        const data = isJson ? await response.json() : await response.text();

        // callback実行
        if (!response.ok) {
            failed(data);
        } else {
            success(data as T);
        }
    } catch (error) {
        failed(error);
    }
}
