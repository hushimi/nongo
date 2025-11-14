<script lang="ts">
  import { onMount } from 'svelte';
  import { goto } from '$app/navigation';
  import { apiConfig } from '$lib/config/api';
  import { SoftwareEngineerApi } from '$lib/types';
  import SoftwareEngineerForm from '$lib/components/SoftwareEngineerForm.svelte';
  import { handleApiPost, handleApiGet } from '$lib/util/api.js';
  import type {
    SoftwareEngineerResponse,
    EditSoftwareEngineerRequest,
    AddNewSoftwareEngineerRequest,
  } from '$lib/types';

  // URLからID取得
  const { data } = $props();
  const tgtId = Number(data.id);

  // エンジニア情報定義
  const api = new SoftwareEngineerApi(apiConfig);
  let errors = $state<Record<string, string>>({});
  let engineer = $state<SoftwareEngineerResponse>();

  /**
   * GET: エンジニア情報単体取得
   */
  async function getEngineer(): Promise<void> {
    handleApiGet<{ id: number }, SoftwareEngineerResponse>(
      api.getEngineersById.bind(api),
      { id: tgtId },
      result => {
        engineer = result;
      },
      error => {
        console.error(error);
      }
    );
  }

  /**
   * 型の確認を行い、paramがAddNewSoftwareEngineerRequestなら更新実行
   */
  function submitWrapper(
    param: EditSoftwareEngineerRequest | AddNewSoftwareEngineerRequest
  ): Promise<void> {
    if ('softwareEngineerUpdateRequest' in param) {
      return handleEdit(param);
    }
    console.warn('想定外の型です');
    return Promise.resolve();
  }

  /**
   * Form入力内容を取得してデータ更新
   */
  async function handleEdit(param: EditSoftwareEngineerRequest): Promise<void> {
    errors = {};
    await handleApiPost(
      api.editSoftwareEngineerRaw.bind(api),
      param,
      async () => goto('/software-engineer'),
      async err => {
        errors = err as Record<string, string>;
      }
    );
  }

  onMount(getEngineer);
</script>

<a href="/software-engineer" class="link">Back to list</a>
<SoftwareEngineerForm {engineer} isEdit={true} onSubmit={submitWrapper} {errors} />
