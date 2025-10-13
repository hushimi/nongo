import { Configuration } from '$lib/types';

export const apiConfig = new Configuration({
    basePath: import.meta.env.PUBLIC_API_BASE
})