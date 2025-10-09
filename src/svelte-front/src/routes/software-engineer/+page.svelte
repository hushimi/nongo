<script lang="ts">
    import { onMount } from "svelte";
    import { goto } from '$app/navigation';
    import type { SoftwareEngineer } from "$lib/types/software-engineer";
    import { getRequest, postRequest } from "$lib/util/api";
    import Fa from "svelte-fa";
    import { faTrash } from "@fortawesome/free-solid-svg-icons";
    import { faEdit } from "@fortawesome/free-regular-svg-icons";

    let engineers: SoftwareEngineer[] = [];

    function fetchData(): void {
        getRequest<SoftwareEngineer[]>(
            '/api/software-engineers',
            undefined,
            (data) => { engineers = data; },
            (err) => { console.log(err) }
        );
    }

    function deleteEngineer(id: number): void {
        postRequest(
            '/api/software-engineers/delete',
            id,
            () => { fetchData(); },
            (err) => { console.log(err); }
        );
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
    <h1 class="text-center font-bold mb-5">Engineers</h1>

    <div class="w-3/5 mx-auto text-end mb-5">
        <a href="/software-engineer/create" class="btn btn-primary">
            create
        </a>
    </div>
    <table class="w-3/5 mx-auto text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400">
        <thead class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
            <tr>
                <th scope="col" class="px-6 py-3">
                    Name
                </th>
                <th scope="col" class="px-6 py-3">
                    Tech Stack
                </th>
                <th scope="col" class="px-6 py-3 text-center">
                    Edit
                </th>
                <th scope="col" class="px-6 py-3 text-center">
                    Delete
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
                    <td class="px-6 py-4 text-center">
                        <button class="block mx-auto cursor-pointer" on:click={() => goto(`/software-engineer/${engineer.id}`)}>
                            <Fa icon={faEdit} color="#00cc00" size="sm" />
                        </button>
                    </td>
                    <td class="px-6 py-4 text-center">
                        <button class="block mx-auto cursor-pointer" on:click={() => deleteEngineer(engineer.id)}>
                            <Fa icon={faTrash} color="#00cc00" size="sm" />
                        </button>
                    </td>
                </tr>
            {/each}
        </tbody>
    </table>
</div>