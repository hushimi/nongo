<script lang="ts">
    import SoftwareEngineerForm from '$lib/components/SoftwareEngineerForm.svelte';
    import { postRequest } from '$lib/util/api';
    import { goto } from '$app/navigation';
    import type { SoftwareEngineerRegist } from '$lib/types/software-engineer';

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
