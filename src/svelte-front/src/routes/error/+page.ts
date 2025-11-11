import type { PageLoad } from './$types';

export const load: PageLoad = ({ url }) => {
  return {
    message: url.searchParams.get('message') || 'エラーが発生しました',
    type: url.searchParams.get('type') || 'error'
  } satisfies { message: string; type: string };
};
