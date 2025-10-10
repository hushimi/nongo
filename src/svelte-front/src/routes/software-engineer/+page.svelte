<script lang="ts">
    import type { SoftwareEngineer } from "$lib/types/software-engineer";
    import Fa from "svelte-fa";
    import { faTrash } from "@fortawesome/free-solid-svg-icons";
    import { faEdit } from "@fortawesome/free-regular-svg-icons";
    import { onMount } from "svelte";
    import { goto } from '$app/navigation';
    import { getRequest, postRequest } from "$lib/util/api";

    let engineers: SoftwareEngineer[] = [];

    /**
     * エンジニア一覧の取得
     */
    function getEngineers(): void {
        getRequest<SoftwareEngineer[]>(
            '/api/software-engineers',
            undefined,
            (data) => { engineers = data; },
            (err) => { console.log(err) }
        );
    }

    /**
     * 指定されたエンジニアデータ削除
     */
    function deleteEngineer(id: number): void {
        postRequest<any>(
            '/api/software-engineers/delete',
            id,
            () => { getEngineers(); },
            (err) => { console.log(err); }
        );
    }

    onMount(getEngineers);
</script>

<a href="/" class="btn btn-accent m-3">nongo</a>

<div class="container mx-auto">
    <h1 class="text-center text-2xl font-bold mb-5">Engineers</h1>

    <div class="w-3/5 mx-auto text-end mb-2">
        <a href="/software-engineer/create" class="btn btn-primary">
            create
        </a>
    </div>
    <div class="overflow-x-auto h-96 w-3/5 mx-auto">
        <table class="table table-xs table-pin-rows">
            <thead>
                <tr class="bg-secondary text-white">
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
                    <tr class="font-bold">
                        <td class="px-6 py-4">
                            {engineer.name}
                        </td>
                        <td class="px-6 py-4">
                            {engineer.techStack}
                        </td>
                        <td class="px-6 py-4 text-center">
                            <button class="block mx-auto cursor-pointer"
                                on:click={() => goto(`/software-engineer/${engineer.id}`)}>
                                <Fa icon={faEdit} color="#000" size="lg" />
                            </button>
                        </td>
                        <td class="px-6 py-4 text-center">
                            <button class="block mx-auto cursor-pointer"
                                on:click={() => deleteEngineer(engineer.id)}>
                                <Fa icon={faTrash} color="#000" size="lg" />
                            </button>
                        </td>
                    </tr>
                {/each}
            </tbody>
        </table>
    </div>
</div>