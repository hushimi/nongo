<script lang="ts">
    let {
        id,
        label,
        type = "text",
        value = $bindable(),
        error,
        autocomplete = "off",
        placeholder,
        required = false
    }: {
        id: string;
        label: string;
        type?: string;
        value: string;
        error?: string;
        autocomplete?: string;
        placeholder?: string;
        required?: boolean;
    } = $props();
</script>

<div>
    <label for={id} class="label">
        <span class="label-text">{label}</span>
        {#if required}
            <span class="label-text-alt text-error">*</span>
        {/if}
    </label>
    <input
        id={id}
        type={type}
        bind:value
        class="input input-bordered w-full {error ? 'input-error' : ''}"
        autocomplete={autocomplete as any}
        placeholder={placeholder}
        required={required}
    />
    {#if error}
        <div class="label">
            <span class="label-text-alt text-error">{error}</span>
        </div>
    {/if}
</div>
