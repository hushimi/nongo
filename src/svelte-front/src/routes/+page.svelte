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

<a href="/" class="btn btn-accent m-3">nongo</a>
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
    {:else}
        <div class="space-y-3">
            <div class="flex gap-3">
                <a href="/signup" class="btn btn-primary flex-1">
                    Sign Up
                </a>
                <a href="/login" class="btn btn-outline btn-primary flex-1">
                    Log In
                </a>
            </div>
            <div class="divider">or</div>
        </div>
    {/if}
  </div>
</div>
