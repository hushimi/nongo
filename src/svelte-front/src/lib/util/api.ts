/**
 * Generic helper for safely calling OpenAPI-generated API methods.
 * Keeps error handling and success logic consistent.
 */
import { ResponseError } from '$lib/types';
type SuccessCallback<T> = (data: T) => void | Promise<void>;
type ErrorCallback = (error: unknown) => void | Promise<void>;

/**
 * Get Request用
 */
export async function handleApiGet<T>(
    apiMethod: () => Promise<T>,
    onSuccess: SuccessCallback<T>,
    onError?: ErrorCallback
) {
    try {
        const result = await apiMethod();
        await onSuccess(result);
    } catch (err) {
        if (onError) await onError(err);
    }
}

/**
 * POSTリクエスト用
 * OpenAPI-CLIで作成したAPIリクエスト用クラスのメソッドを実行
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
    } catch (err: any) {
        console.error("API Error caught in handleApiPost:", err);

        if (!onError) return;

        // errがresponseプロパティを持っている場合、エラーオブジェクト抽出
        const response = err?.response as Response | undefined;
        if (response instanceof Response) {
            try {
                const data = await response.json();
                await onError(data);
            } catch {
                await onError({ general: "サーバーエラーの解析に失敗しました。" });
            }
            return;
        }

        // 不明なレスポンス内容
        if (typeof err === "object" && err !== null) {
            await onError(err);
            return;
        }

        // Fallback
        await onError({ general: "予期せぬエラーが発生しました。" });
    }
}
