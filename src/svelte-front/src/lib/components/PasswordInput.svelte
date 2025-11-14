<script lang="ts">
  import { faEye, faEyeSlash } from '@fortawesome/free-regular-svg-icons';
  import FontAwesome from 'svelte-fa';

  let {
    id,
    label,
    value = $bindable(),
    error,
    autocomplete = "off",
    placeholder,
    required = false,
    displayRule = false
  }: {
    id: string;
    label: string;
    value: string;
    error?: string;
    autocomplete?: string;
    placeholder?: string;
    required?: boolean;
    displayRule?: boolean;
  } = $props();

  let showPassword = $state(false);
</script>

<div>
  <label for={id} class="label">
    <span class="label-text">{label}</span>
    {#if required}
      <span class="badge badge-sm badge-soft badge-error">必須</span>
    {/if}
  </label>
  <div class="relative">
    <input
      id={id}
      type={showPassword ? "text" : "password"}
      bind:value
      class="input input-bordered w-full pr-10 {error ? 'input-error' : ''}"
      autocomplete={autocomplete as any}
      placeholder={placeholder}
      required={required}
    />
    <button
      type="button"
      class="absolute right-2 top-1 btn btn-ghost btn-sm btn-circle"
      onclick={() => showPassword = !showPassword}
      aria-label={showPassword ? "パスワードを非表示" : "パスワードを表示"}
    >
        {#if showPassword}
            <FontAwesome icon={faEyeSlash} />
        {:else}
            <FontAwesome icon={faEye} />
        {/if}
    </button>
  </div>
  {#if error}
      <div class="label">
          <span class="label-text-alt text-error">{error}</span>
      </div>
  {/if}
  {#if displayRule}
    <p class="text-xs text-gray-400 mt-2">
        パスワードは8文字以上で、少なくとも1つの数字と1つの記号(!@?_#&)を含む必要があります。
    </p>
  {/if}
</div>
