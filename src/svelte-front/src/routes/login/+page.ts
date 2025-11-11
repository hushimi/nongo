import type { PageLoad } from './$types';

export const load: PageLoad = ({ url }) => {
  return {
    verified: url.searchParams.get('verified') === 'true'
  } satisfies { verified: boolean };
};
