<script lang="ts">
    import { goto } from '$app/navigation';
    import { AuthenticationApi } from '$lib/types';
    import { apiConfig } from '$lib/config/api';
    import { handleApiPost } from '$lib/util/api';
    import TextInput from '$lib/components/TextInput.svelte';
    import PasswordInput from '$lib/components/PasswordInput.svelte';

    const api = new AuthenticationApi(apiConfig);
    let userName = $state('');
    let email = $state('');
    let password = $state('');
    let passwordConfirmation = $state('');
    let errors = $state<Record<string, string>>({});
    let isLoading = $state(false);

    // フォームの有効性をチェック
    let isFormValid = $derived(
        userName.trim() !== '' &&
        email.trim() !== '' &&
        password.trim() !== '' &&
        passwordConfirmation.trim() !== '' &&
        password === passwordConfirmation
    );

    // パスワード確認エラー
    let passwordConfirmationError = $derived(
        passwordConfirmation !== '' && password !== passwordConfirmation
            ? 'パスワードが一致しません'
            : undefined
    );

    async function handleSubmit(event: Event) {
        event.preventDefault();
        errors = {};
        isLoading = true;

        await handleApiPost(
            api.signupRaw.bind(api),
            {
                signupRequest: {
                    userName,
                    email,
                    password,
                },
            },
            async () => {
                goto('/login');
            },
            async (err) => {
                errors = err as Record<string, string>;
            }
        );

        isLoading = false;
    }
</script>

<div class="min-h-screen flex items-center justify-center bg-base-200">
  <div class="card w-full max-w-md shadow-2xl bg-base-100 my-10">
    <div class="card-body">
      <h2 class="card-title text-2xl justify-center mb-4">Sign Up</h2>
      <p class="text-center text-base-content/70 mb-6">
          Create your account to get started
      </p>

      <form onsubmit={handleSubmit} class="space-y-4">
        {#if errors.general}
          <div class="alert alert-error">
            <span>{errors.general}</span>
          </div>
        {/if}

        <TextInput
          id="username"
          label="ユーザ名"
          type="text"
          bind:value={userName}
          error={errors.userName}
          placeholder=""
          required={true}
          autocomplete="off"
        />

        <TextInput
          id="email"
          label="Email"
          type="email"
          bind:value={email}
          error={errors.email}
          placeholder=""
          required={true}
          autocomplete="off"
        />

        <PasswordInput
          id="password"
          label="パスワード"
          bind:value={password}
          error={errors.password}
          placeholder=""
          required={true}
          autocomplete="new-password"
          displayRule={true}
        />

        <PasswordInput
          id="passwordConfirmation"
          label="パスワード（確認）"
          bind:value={passwordConfirmation}
          error={passwordConfirmationError}
          placeholder=""
          required={false}
          autocomplete="new-password"
        />

        <div class="card-actions justify-end mt-6">
          <button
            type="submit"
            class="btn btn-primary w-full"
            disabled={isLoading || !isFormValid}
          >
          {#if isLoading}
            <span class="loading loading-spinner"></span>
            Signing up...
          {:else}
            Sign Up
          {/if}
          </button>
        </div>

        <div class="text-center mt-4">
            <a href="/login" class="link link-primary">
                Already have an account? Log in
            </a>
        </div>
      </form>
    </div>
  </div>
</div>
