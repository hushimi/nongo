package bushigen.nongo.Todo;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private List<Todo> todos = new ArrayList<>();
    private AtomicLong counter = new AtomicLong();

    @GetMapping
    public List<Todo> getTodos() {
        System.out.println("GET /api/todos was called!!!");
        return todos;
    }

    @PostMapping
    public Todo addTodo(@RequestBody Todo todo) {
        System.out.println("POST /api/todos was called");
        todo.setId(counter.incrementAndGet());
        todos.add(todo);
        return todo;
    }

    @PutMapping("/{id}")
    public Todo updateTodo(@PathVariable Long id, @RequestBody Todo updated) {
        System.out.println("PUT /api/todos was called");
        for (Todo todo : todos) {
            if (todo.getId().equals(id)) {
                todo.setTask(updated.getTask());
                todo.setCompleted(updated.isCompleted());
                return todo;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable Long id) {
        System.out.println("DELETE /api/todos was called");
        todos.removeIf(todo -> todo.getId().equals(id));
    }
}
