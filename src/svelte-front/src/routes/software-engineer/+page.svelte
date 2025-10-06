<script lang="ts">
    import { onMount } from "svelte";

    type Se = {
        name: string;
        techStack: string;
    }

    let engineers: Se[] = [];
    async function fetchData(): Promise<void> {
        const res = await fetch('/api/software-engineers');
        const data: Se[] = await res.json();
        engineers = data;
    }

    onMount(fetchData);
</script>

<a
    href="/"
    class="w-4 text-center mx-auto px-4 py-2 rounded bg-blue-100 text-blue-700 hover:bg-blue-200 transition"
>
    nongo
</a>
<div class="container mx-auto">
    <h1 class="text-neutral-800 text-center font-bold mb-5">Engineers</h1>

    <table class="w-3/5 mx-auto text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400">
        <thead class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
            <tr>
                <th scope="col" class="px-6 py-3">
                    Name
                </th>
                <th scope="col" class="px-6 py-3">
                    Tech Stack
                </th>
            </tr>
        </thead>
        <tbody>
            {#each engineers as engineer}
                <tr class="bg-white border-b dark:bg-gray-800 dark:border-gray-700 border-gray-200">
                    <td class="px-6 py-4">
                        {engineer.name}
                    </td>
                    <td class="px-6 py-4">
                        {engineer.techStack}
                    </td>
                </tr>
            {/each}
        </tbody>
    </table>
</div>