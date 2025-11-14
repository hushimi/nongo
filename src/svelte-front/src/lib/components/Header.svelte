<script lang="ts">
  import { onMount } from 'svelte';
  import { goto, afterNavigate } from '$app/navigation';
  import { AuthenticationApi } from '$lib/types';
  import { apiConfig } from '$lib/config/api';
  import { handleApiPost } from '$lib/util/api';
  import { checkAuthentication, clearAuthCache } from '$lib/util/auth';

  const api = new AuthenticationApi(apiConfig);
  let isAuthenticated = $state(false);
  let isLoading = $state(true);

  async function updateAuthStatus() {
    isLoading = true;
    isAuthenticated = await checkAuthentication();
    isLoading = false;
  }

  onMount(async () => {
    await updateAuthStatus();
  });

  afterNavigate(async () => {
    await updateAuthStatus();
  });

  async function handleLogout() {
    await handleApiPost(
      api.logoutRaw.bind(api),
      {},
      async () => {
        clearAuthCache();
        isAuthenticated = false;
        goto('/');
      },
      async err => {
        console.error('ログアウトエラー:', err);
      }
    );
  }
</script>

<header class="navbar bg-base-100 shadow-lg">
  <div class="flex-1">
    <a href="/" class="btn btn-ghost text-xl">nongo</a>
  </div>
  <div class="flex-none gap-2">
    {#if isLoading}
      <span class="loading loading-spinner loading-sm"></span>
    {:else if isAuthenticated}
      <button class="btn btn-outline btn-error" onclick={handleLogout}>Logout</button>
    {:else}
      <div class="flex gap-2">
        <a href="/signup" class="btn btn-primary">Sign Up</a>
        <a href="/login" class="btn btn-outline btn-primary">Log In</a>
      </div>
    {/if}
  </div>
</header>
