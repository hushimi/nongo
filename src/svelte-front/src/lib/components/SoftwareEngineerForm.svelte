<script lang="ts">
    import type {
        SoftwareEngineer,
        AddNewSoftwareEngineerRequest,
        EditSoftwareEngineerRequest
    } from "$lib/types";

    // propsの型定義・ParentComponentから値取得
    type Props = {
        engineer?: SoftwareEngineer;
        isEdit: boolean;
        onSubmit: (param: AddNewSoftwareEngineerRequest | EditSoftwareEngineerRequest) => Promise<void>;
    };
    const { engineer, isEdit, onSubmit }: Props = $props();

    // reactiveな変数宣言
    let name = $state<string>(engineer?.name ?? '');
    let techStack = $state<string>(engineer?.techStack ?? '');
    $effect(() => {
        if (engineer) {
            name = engineer.name ?? '';
            techStack = engineer.techStack ?? '';
        }
    });

    /**
     * Form Submit時、parentのonSubmitで指定された関数実行
     */
    function handleSubmit(event: Event) {
        event.preventDefault();
        if (isEdit && engineer) {
            // 更新
            onSubmit?.({
                softwareEngineer: {
                    id: engineer?.id,
                    name,
                    techStack
                }
            });
        } else {
            // 登録
            onSubmit?.({
                softwareEngineerCreateRequest: {
                    name,
                    techStack
                }
            });
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
