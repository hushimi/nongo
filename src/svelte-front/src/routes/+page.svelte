<script lang="ts">
  import { onMount } from 'svelte';
  import { checkAuthentication } from '$lib/util/auth';

  let isAuthenticated = $state(false);
  let isLoading = $state(true);

  onMount(async () => {
    isAuthenticated = await checkAuthentication();
    isLoading = false;
  });
</script>

<div class="max-w-sm rounded overflow-hidden shadow-lg mx-auto">
  <div class="px-6 py-4">
    <div class="font-bold text-xl mb-4">TOP PAGE</div>
    {#if isLoading}
      <div class="flex justify-center">
        <span class="loading loading-spinner"></span>
      </div>
    {:else if isAuthenticated}
      <div class="space-y-3">
        <a
          href="/software-engineer"
          class="btn btn-secondary w-full"
        >
          SoftwareEngineer List
        </a>
      </div>
    {/if}
  </div>
</div>
