/**
 * Authentication utility functions
 * Since JWT token is stored in HTTP-only cookie, we check authentication
 * by making a request to a protected endpoint
 */

let isAuthenticatedCache: boolean | null = null;

/**
 * Check if user is authenticated by attempting to access a protected endpoint
 * Returns true if authenticated, false otherwise
 */
export async function checkAuthentication(): Promise<boolean> {
    if (isAuthenticatedCache !== null) {
        return isAuthenticatedCache;
    }

    try {
        const apiBase = import.meta.env.PUBLIC_API_BASE || 'http://localhost:8081';
        const response = await fetch(`${apiBase}/api/software-engineers`, {
            method: 'GET',
            credentials: 'include', // Important: include cookies
        });

        const authenticated = response.ok;
        isAuthenticatedCache = authenticated;
        return authenticated;
    } catch (error) {
        isAuthenticatedCache = false;
        return false;
    }
}

/**
 * Clear authentication cache (call after logout or login)
 */
export function clearAuthCache(): void {
    isAuthenticatedCache = null;
}
