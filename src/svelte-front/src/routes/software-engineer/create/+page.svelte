<script lang="ts">
    import { SoftwareEngineerApi } from "$lib/types";
    import { apiConfig } from "$lib/config/api";
    import SoftwareEngineerForm from '$lib/components/SoftwareEngineerForm.svelte';
    import { goto } from '$app/navigation';
    import type {
        AddNewSoftwareEngineerRequest,
        EditSoftwareEngineerRequest
    } from "$lib/types";

    const api = new SoftwareEngineerApi(apiConfig);

    /**
     * 型の確認を行い、paramがAddNewSoftwareEngineerRequestなら登録実行
     */
     function submitWrapper(param: AddNewSoftwareEngineerRequest | EditSoftwareEngineerRequest): Promise<void> {
        if ('softwareEngineerCreateRequest' in param) {
            return handleCreate(param);
        }
        console.warn('Type is not expected');
        return Promise.resolve();
    }

    /**
     * Form入力内容を取得してデータ登録
     * 登録後一覧に戻る
     */
    async function handleCreate(param: AddNewSoftwareEngineerRequest): Promise<void> {
        try {
            await api.addNewSoftwareEngineer(param);
            goto('/software-engineer');
        } catch (error) {
            console.error('Failed to create record', error);
        }
    }

</script>

<a href="/software-engineer" class="link">Back to list</a>
<SoftwareEngineerForm onSubmit={submitWrapper} isEdit={false} />
