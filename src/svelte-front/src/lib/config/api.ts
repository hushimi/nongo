import { Configuration } from '$lib/types';

export const apiConfig = new Configuration({
    basePath: import.meta.env.PUBLIC_API_BASE,
    credentials: 'include' // Include cookies (for JWT token) in requests
})