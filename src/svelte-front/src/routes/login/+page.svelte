<script lang="ts">
  import { goto } from '$app/navigation';
  import { AuthenticationApi } from '$lib/types';
  import { apiConfig } from '$lib/config/api';
  import { handleApiPost } from '$lib/util/api';
  import { clearAuthCache } from '$lib/util/auth';
  import TextInput from '$lib/components/TextInput.svelte';
  import PasswordInput from '$lib/components/PasswordInput.svelte';
  const api = new AuthenticationApi(apiConfig);
  let userName = $state('');
  let password = $state('');
  let errors = $state<Record<string, string>>({});
  let isLoading = $state(false);

  // Get data from load function
  let { data }: { data: { verified: boolean } } = $props();

  // Check if all fields are filled
  let isFormValid = $derived(userName.trim() !== '' && password.trim() !== '');

  async function handleSubmit(event: Event) {
    event.preventDefault();
    errors = {};
    isLoading = true;

    await handleApiPost(
      api.loginRaw.bind(api),
      {
        loginRequest: {
          userName,
          password,
        },
      },
      async () => {
        clearAuthCache();
        goto('/');
      },
      async err => {
        errors = err as Record<string, string>;
      }
    );

    isLoading = false;
  }
</script>

<div class="flex items-center justify-center bg-base-200">
  <div class="card w-full max-w-md shadow-2xl bg-base-100 my-10 flex-grow-1">
    <div class="card-body">
      <h2 class="card-title text-2xl justify-center mb-4">Log In</h2>
      <p class="text-center text-base-content/70 mb-6">Sign in to your account</p>

      {#if data.verified}
        <div class="alert alert-success">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            class="stroke-current shrink-0 h-6 w-6"
            fill="none"
            viewBox="0 0 24 24"
          >
            <path
              stroke-linecap="round"
              stroke-linejoin="round"
              stroke-width="2"
              d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"
            />
          </svg>
          <span>アカウントが認証されました。ログインしてください。</span>
        </div>
      {/if}

      <form onsubmit={handleSubmit} class="space-y-4">
        {#if errors.general}
          <div class="alert alert-error">
            <span>{errors.general}</span>
          </div>
        {/if}

        <TextInput
          id="user_name"
          label="ユーザ名"
          type="text"
          bind:value={userName}
          error={errors.userName}
          placeholder=""
          required={false}
          autocomplete="off"
        />

        <PasswordInput
          id="password"
          label="パスワード"
          bind:value={password}
          error={errors.password}
          placeholder=""
          required={false}
          autocomplete="off"
        />

        <div class="card-actions justify-end mt-6">
          <button type="submit" class="btn btn-primary w-full" disabled={isLoading || !isFormValid}>
            {#if isLoading}
              <span class="loading loading-spinner"></span>
              Logging in...
            {:else}
              Log In
            {/if}
          </button>
        </div>

        <div class="text-center mt-4">
          <a href="/signup" class="link link-primary"> Don't have an account? Sign up </a>
        </div>
      </form>
    </div>
  </div>
</div>
