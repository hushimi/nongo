<script lang="ts">
  import favicon from '$lib/assets/favicon.svg';
  import Header from '$lib/components/Header.svelte';
  let { children } = $props();
  import '../app.css';
</script>

<svelte:head>
  <link rel="icon" href={favicon} />
</svelte:head>

<div class="flex flex-col min-h-screen bg-base-200">
  <Header />
  <main class="grow">
    {@render children?.()}
  </main>
</div>
