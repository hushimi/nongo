<script lang="ts">
    import type { SoftwareEngineerRegist } from '$lib/types/software-engineer';
    import SoftwareEngineerForm from '$lib/components/SoftwareEngineerForm.svelte';
    import { goto } from '$app/navigation';
    import { postRequest } from '$lib/util/api';

    /**
     * Form入力内容を取得してデータ登録
     * 登録後一覧に戻る
     */
    async function handleCreate(param: SoftwareEngineerRegist) {
        postRequest(
            '/api/software-engineers',
            param,
            () => { goto('/software-engineer'); },
            (err) => { console.log(err); }
        )
    }
</script>

<a href="/software-engineer" class="link">Back to list</a>
<SoftwareEngineerForm onSubmit={handleCreate} isEdit={false} />
