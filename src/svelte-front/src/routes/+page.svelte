<script lang="ts">
    import { onMount } from 'svelte';

    type Todo = {
        id: number;
        task: string;
        completed: boolean;
    };

    let todos: Todo[] = [];
    let newTask: string = '';

    async function fetchTodos(): Promise<void> {
        const res = await fetch('/api/todos');
        const data: Todo[] = await res.json();
        todos = data;
    }

    async function addTodo(): Promise<void> {
        if (!newTask) return;
        const res = await fetch('/api/todos', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ task: newTask, completed: false })
        });
        const todo: Todo = await res.json();
        todos = [...todos, todo];
        newTask = '';
    }

    async function toggleTodo(todo: Todo): Promise<void> {
        await fetch(`/api/todos/${todo.id}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ ...todo, completed: !todo.completed })
        });
        todo.completed = !todo.completed;
    }

    async function deleteTodo(id: number): Promise<void> {
        console.log(`Deleting todo with id: ${id}`);
        await fetch(`/api/todos/${id}`, { method: 'DELETE' });
        todos = todos.filter(t => t.id !== id);
    }

    onMount(fetchTodos);
</script>

<h1 class="ms-3 text-blue-600">tintin List</h1>
<input bind:value={newTask} placeholder="New task" class="ms-3" />
<button on:click={addTodo} class="ms-3">Add</button>

<ul class="list-none ms-3">
    {#each todos as todo}
        <li>
        <input type="checkbox" checked={todo.completed} on:change={() => toggleTodo(todo)} />
        {todo.task}
        <button on:click={() => deleteTodo(todo.id)}>Delete</button>
        </li>
    {/each}
</ul>
