<!-- todo validationはサーバ側で行うようにする -->
<script lang="ts">
    import { goto } from '$app/navigation';

    let name: string = '';
    let techStack: string = '';
    let error: string = '';

    /**
     * create new record
     */
    async function createEngineer(): Promise<void> {
        error = '';
        if (!name || !techStack) {
            error = 'Both fields are required.';
            return;
        }
        try {
            const res: Response = await fetch('/api/software-engineers', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ name, techStack })
            });
            if (!res.ok) {
                error = 'Failed to create engineer.';
                return;
            }
            // Redirect to /software-engineer on success
            goto('/software-engineer');
        } catch (e) {
            error = 'Error occurred.';
        }
    }
</script>

<a href="/software-engineer" class="block mb-4 text-blue-600 hover:underline">← Back to list</a>

<div class="max-w-md mx-auto bg-white p-8 rounded shadow">
    <h2 class="text-2xl font-bold mb-4 text-center">Create Software Engineer</h2>
    {#if error}
        <div class="mb-2 text-red-600">{error}</div>
    {/if}

    <form on:submit|preventDefault={createEngineer}>
        <div class="mb-4">
            <label class="block mb-1 font-semibold" for="name">Name</label>
            <input class="w-full border rounded px-3 py-2" bind:value={name} id="name" autocomplete="off" />
        </div>
        <div class="mb-4">
            <label class="block mb-1 font-semibold" for="techStack">Tech Stack</label>
            <input class="w-full border rounded px-3 py-2" bind:value={techStack} id="techStack" autocomplete="off" />
        </div>
        <button type="submit" class="btn btn-primary w-full">Create</button>
    </form>
</div>
