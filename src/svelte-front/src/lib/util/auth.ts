/**
 * Authentication utility functions
 * Since JWT token is stored in HTTP-only cookie, we check authentication
 * by making a request to the is-token-valid endpoint
 */

import { AuthenticationApi } from '$lib/types';
import { apiConfig } from '$lib/config/api';

let isAuthenticatedCache: boolean | null = null;
const api = new AuthenticationApi(apiConfig);

/**
 * Check if user is authenticated using the is-token-valid API
 * Returns true if authenticated, false otherwise
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
 * Clear authentication cache (call after logout or login)
 */
export function clearAuthCache(): void {
  isAuthenticatedCache = null;
}
