import { Configuration } from '$lib/types';

export const apiConfig = new Configuration({
  basePath: import.meta.env.PUBLIC_API_BASE,
  credentials: 'include' // リクエストにCookie（JWTトークン用）を含める
})