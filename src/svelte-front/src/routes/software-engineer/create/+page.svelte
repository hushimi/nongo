<script lang="ts">
    import { goto } from '$app/navigation';
    import { SoftwareEngineerApi } from "$lib/types";
    import { apiConfig } from "$lib/config/api";
    import SoftwareEngineerForm from '$lib/components/SoftwareEngineerForm.svelte';
    import type {
        AddNewSoftwareEngineerRequest,
        EditSoftwareEngineerRequest
    } from "$lib/types";
    import { handleApiPost } from '$lib/util/api';

    const api = new SoftwareEngineerApi(apiConfig);
    let errors: Record<string, string> = {};

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
     * エラー発生時、フォームにエラー表示
     */
    async function handleCreate(param: AddNewSoftwareEngineerRequest): Promise<void> {
        errors = {};
        await handleApiPost(
            api.addNewSoftwareEngineerRaw.bind(api),
            param,
            async () => {
                goto('/software-engineer');
            },
            async (err) => {
                console.error("API error:", err);
                errors = err as Record<string, string>;
            }
        );
    }

</script>

<a href="/software-engineer" class="link">Back to list</a>
<SoftwareEngineerForm onSubmit={submitWrapper} {errors} isEdit={false} />
