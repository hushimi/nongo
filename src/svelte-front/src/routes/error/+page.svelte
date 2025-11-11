<script lang="ts">
  import { goto } from '$app/navigation';

  // Get data from load function
  let { data }: { data: { message: string; type: string } } = $props();
</script>

<div class="min-h-screen flex items-center justify-center bg-base-200">
  <div class="card w-full max-w-md shadow-2xl bg-base-100 my-10">
    <div class="card-body">
      <h2 class="card-title text-2xl justify-center mb-4">
        {#if data.type === 'verification'}
          認証エラー
        {:else}
          エラー
        {/if}
      </h2>

      <div class="space-y-4">
        {#if data.type === 'verification'}
          <div class="alert alert-warning">
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
                d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z"
              />
            </svg>
            <span>{data.message}</span>
          </div>
        {:else}
          <div class="alert alert-error">
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
                d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z"
              />
            </svg>
            <span>{data.message}</span>
          </div>
        {/if}

        <div class="card-actions justify-center mt-6">
          <button type="button" class="btn btn-primary" onclick={() => goto('/')}>
            トップページへ戻る
          </button>
        </div>

        {#if data.type === 'verification'}
          <div class="text-center mt-4">
            <a href="/signup" class="link link-primary"> 新規登録ページへ </a>
          </div>
        {/if}
      </div>
    </div>
  </div>
</div>
