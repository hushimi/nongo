import adapter from '@sveltejs/adapter-static';
import { vitePreprocess } from '@sveltejs/vite-plugin-svelte';
import { resolve } from 'path';

/** @type {import('@sveltejs/kit').Config} */
const config = {
	// Consult https://svelte.dev/docs/kit/integrations
	// for more information about preprocessors
	preprocess: vitePreprocess(),

	kit: {
        adapter: adapter({
          pages: resolve('..', 'main', 'resources', 'static'),
          assets: resolve('..', 'main', 'resources', 'static'),
          fallback: 'index.html'
        }),
        paths: {
          base: ''
        }
    }
};

export default config;
