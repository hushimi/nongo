<script lang="ts">
    import { onMount } from 'svelte';
    import { goto } from '$app/navigation';
    import SoftwareEngineerForm from '$lib/components/SoftwareEngineerForm.svelte';
    import { getRequest, postRequest } from '$lib/util/api';
    import type { SoftwareEngineer, SoftwareEngineerRegist } from '$lib/types/software-engineer';
    const { data } = $props();

    const tgtId = data.id;
    let engineer = $state<SoftwareEngineer | undefined>(undefined);

    function getEngineer(): void {
        getRequest<SoftwareEngineer>(
            `/api/software-engineers/${tgtId}`,
            undefined,
            (person) => { engineer = person; },
            (err) => { console.log(err); }
        );
    }

    async function handleEdit(param: SoftwareEngineer | SoftwareEngineerRegist): Promise<void> {
        const payload = 'id' in param ? param : { ...param, id: tgtId };
        await postRequest<unknown>(
            '/api/software-engineers/edit',
            payload,
            () => { goto('/software-engineer'); },
            (err) => { console.log(err); }
        );
    }

    onMount(getEngineer);
</script>
<a href="/software-engineer" class="link">Back to list</a>
<SoftwareEngineerForm engineer={engineer} isEdit={true} onSubmit={handleEdit} />
