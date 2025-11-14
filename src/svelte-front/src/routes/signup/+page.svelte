<script lang="ts">
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
  let showSuccessMessage = $state(false);

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
        showSuccessMessage = true;
        // フォームをリセット
        userName = '';
        email = '';
        password = '';
        passwordConfirmation = '';
      },
      async err => {
        errors = err as Record<string, string>;
        console.error('サインアップエラー:', err);
      }
    );

    isLoading = false;
  }
</script>

<div class="min-h-screen flex items-center justify-center bg-base-200">
  <div class="card w-full max-w-md shadow-2xl bg-base-100 my-10">
    <div class="card-body">
      <h2 class="card-title text-2xl justify-center mb-4">Sign Up</h2>
      <p class="text-center text-base-content/70 mb-6">Create your account to get started</p>

      {#if showSuccessMessage}
        <div class="alert alert-success">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            class="stroke-current shrink-0 h-6 w-6"
            fill="none"
            viewBox="0 0 24 24">
            <path
              stroke-linecap="round"
              stroke-linejoin="round"
              stroke-width="2"
              d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z" />
          </svg>
          <span>アカウント認証メールを送信しました</span>
        </div>
        <div class="text-center mt-4">
          <a href="/login" class="link link-primary">ログインページへ</a>
        </div>
      {:else}
        <form onsubmit={handleSubmit} class="space-y-4">
          {#if errors.general}
            <div class="alert alert-error"><span>{errors.general}</span></div>
          {/if}

          <TextInput
            id="username"
            label="ユーザ名"
            type="text"
            bind:value={userName}
            error={errors.userName}
            placeholder=""
            required={true}
            autocomplete="off" />

          <TextInput
            id="email"
            label="Email"
            type="email"
            bind:value={email}
            error={errors.email}
            placeholder=""
            required={true}
            autocomplete="off" />

          <PasswordInput
            id="password"
            label="パスワード"
            bind:value={password}
            error={errors.password}
            placeholder=""
            required={true}
            autocomplete="new-password"
            displayRule={true} />

          <PasswordInput
            id="passwordConfirmation"
            label="パスワード（確認）"
            bind:value={passwordConfirmation}
            error={passwordConfirmationError}
            placeholder=""
            required={false}
            autocomplete="new-password" />

          <div class="card-actions justify-end mt-6">
            <button
              type="submit"
              class="btn btn-primary w-full"
              disabled={isLoading || !isFormValid}>
              {#if isLoading}
                <span class="loading loading-spinner"></span>
                Signing up...
              {:else}
                Sign Up
              {/if}
            </button>
          </div>

          <div class="text-center mt-4">
            <a href="/login" class="link link-primary">Already have an account? Log in</a>
          </div>
        </form>
      {/if}
    </div>
  </div>
</div>
