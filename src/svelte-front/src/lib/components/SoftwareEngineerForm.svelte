<script lang="ts">
    import type {
        AddNewSoftwareEngineerRequest,
        EditSoftwareEngineerRequest,
        SoftwareEngineerResponse
    } from "$lib/types";

    // 親Componentから値取得
    const props = $props<{
        engineer?: SoftwareEngineerResponse;
        isEdit: boolean;
        onSubmit: (param: AddNewSoftwareEngineerRequest | EditSoftwareEngineerRequest) => Promise<void>;
        errors?: Record<string, string>;
    }>();

    let name = $state<string>(props.engineer?.name ?? '');
    let techStack = $state<string>(props.engineer?.techStack ?? '');

    $effect(() => {
        if (props.engineer) {
            name = props.engineer.name ?? '';
            techStack = props.engineer.techStack ?? '';
        }
    });

    /**
     * Form Submit時、parentのonSubmitで指定された関数実行
     */
    function handleSubmit(event: Event) {
        event.preventDefault();
        if (props.isEdit && props.engineer) {
            // 更新
            props.onSubmit?.({
                softwareEngineerUpdateRequest: {
                    id: props.engineer.id as number,
                    name,
                    techStack
                }
            });
        } else {
            // 登録
            props.onSubmit?.({
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
        <!-- 全体エラー -->
        {#if props.errors?.general}
            <p class="text-error text-sm">{props.errors.general}</p>
        {/if}

        <div>
            <label for="name" class="block">Name</label>
            <input
                id="name"
                type="text"
                bind:value={name}
                class="border rounded p-2 w-full"
                autocomplete="off"
            />
            {#if props.errors?.name}
                <p class="text-error text-sm">{props.errors.name}</p>
            {/if}
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
            {#if props.errors?.techStack}
                <p class="text-error text-sm">{props.errors.techStack}</p>
            {/if}
        </div>
        <button type="submit" class="btn btn-primary">
            {props.isEdit ? 'Update' : 'Create'}
        </button>
    </form>
</div>
