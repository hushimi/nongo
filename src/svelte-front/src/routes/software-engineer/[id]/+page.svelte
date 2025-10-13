<script lang="ts">
    import { SoftwareEngineerApi } from "$lib/types";
    import { apiConfig } from "$lib/config/api";
    import SoftwareEngineerForm from '$lib/components/SoftwareEngineerForm.svelte';
    import { onMount } from 'svelte';
    import { goto } from '$app/navigation';
    import type {
        SoftwareEngineerResponse,
        AddNewSoftwareEngineerRequest,
        EditSoftwareEngineerRequest,
    } from "$lib/types";
    const { data } = $props();

    // URLからID取得
    const tgtId = Number(data.id);
    const api = new SoftwareEngineerApi(apiConfig);
    let engineer = $state<SoftwareEngineerResponse | undefined>(undefined);

    /**
     * GET: エンジニア情報単体取得
     */
    async function getEngineer(): Promise<void> {
        try {
            engineer = await api.getEngineersById({id: tgtId});
        } catch (error) {
            console.error('Failed to fetch record', error);
        }
    }

    /**
     * 型の確認を行い、paramがAddNewSoftwareEngineerRequestなら更新実行
     */
     function submitWrapper(param: AddNewSoftwareEngineerRequest | EditSoftwareEngineerRequest): Promise<void> {
        if ('softwareEngineer' in param) {
            console.log()
            return handleEdit(param);
        }
        console.warn('Type is not expected');
        return Promise.resolve();
    }

    /**
     * Form入力内容を取得してデータ更新
     */
    async function handleEdit(param: EditSoftwareEngineerRequest): Promise<void> {
        try {
            await api.editSoftwareEngineer(param);
            goto('/software-engineer');
        } catch (error) {
            console.error('Failed to create record', error);
        }
    }

    onMount(getEngineer);
</script>

<a href="/software-engineer" class="link">Back to list</a>
<SoftwareEngineerForm engineer={engineer} isEdit={true} onSubmit={submitWrapper} />
