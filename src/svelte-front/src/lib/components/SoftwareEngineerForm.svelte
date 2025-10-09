<script lang="ts">
    import type { SoftwareEngineer, SoftwareEngineerRegist } from "$lib/types/software-engineer";
    type Props = {
        isEdit: boolean;
        onSubmit: (param: SoftwareEngineer | SoftwareEngineerRegist) => Promise<void>;
        engineer?: SoftwareEngineer;
    };

    const { engineer, isEdit, onSubmit }: Props = $props();
    let name = $state(engineer?.name ?? '');
    let techStack = $state(engineer?.techStack ?? '');
    $effect(() => {
        if (engineer) {
            name = engineer.name ?? '';
            techStack = engineer.techStack ?? '';
        }
    });

    function handleSubmit(event: Event) {
        event.preventDefault();
        if (isEdit) {
            // 更新
            onSubmit?.({ id: engineer?.id, name: name, techStack: techStack });
        } else {
            // 登録
            onSubmit?.({ name: name, techStack: techStack });
        }
    }
</script>

<div class="card w-2/5 mx-auto shadow-sm bg-base-100">
    <form onsubmit={handleSubmit} class="space-y-4 card-body">
        <div>
            <label for="name" class="block">Name</label>
            <input
                id="name"
                type="text"
                bind:value={name}
                class="border rounded p-2 w-full"
                autocomplete="off"
            />
        </div>
        <div>
            <label for="techStack" class="block">Tech Stack</label>
            <input
                id="techStack"
                type="text"
                bind:value={techStack}
                class="border rounded p-2 w-full"
                autocomplete="off"
            />
        </div>
        <button type="submit" class="btn btn-primary">
            {isEdit ? 'Update' : 'Create'}
        </button>
    </form>
</div>
