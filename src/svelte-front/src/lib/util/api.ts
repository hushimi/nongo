/**
 * Sends a GET request with optional query parameters.
 * @template T
 * @param {string} url - The endpoint URL.
 * @param {Record<string, any>|undefined} params - Object representing query parameters.
 * @param {(data: T) => void} success - Called with response data on success.
 * @param {(error: any) => void} failed - Called with error on failure.
 * @return {Promise<void>}
 */
export async function getRequest<T>(
    url: string,
    params: Record<string, unknown> | undefined,
    success: (data: T) => void,
    failed: (error: unknown) => void
): Promise<void> {
    try {
        const query = params
            ? `?${new URLSearchParams(params as Record<string, string>).toString()}`
            : '';

        const response = await fetch(`${url}${query}`, {
            method: 'GET',
            headers: { 'Content-Type': 'application/json' },
        });

        const contentType = response.headers.get('content-type');
        const isJson = contentType?.includes('application/json');

        const data = isJson ? await response.json() : await response.text();

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
 * Sends a POST request with a JSON body.
 * @template T
 * @param {string} url - The endpoint URL.
 * @param {any} body - Data to send as the request body (will be JSON.stringified).
 * @param {(data: T) => void} success - Called with response data on success.
 * @param {(error: any) => void} failed - Called with error on failure.
 * @return {Promise<void>}
 */
export async function postRequest<T>(
    url: string,
    body: unknown,
    success: (data: T) => void,
    failed: (error: unknown) => void
): Promise<void> {
    try {
        const response = await fetch(url, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(body),
        });

        const contentType = response.headers.get('content-type');
        const isJson = contentType?.includes('application/json');

        const data = isJson ? await response.json() : await response.text();

        if (!response.ok) {
            failed(data);
        } else {
            success(data as T);
        }
    } catch (error) {
        failed(error);
    }
}
