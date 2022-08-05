package jana60.controller;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import jana60.model.Todo;
import jana60.repository.TodoRepository;

@RestController
@CrossOrigin
@RequestMapping("/api/todos")
public class TodoController {

  @Autowired
  private TodoRepository repo;

  @GetMapping
  public List<Todo> getAll() {
    return (List<Todo>) repo.findAll();
  }

  @GetMapping("/{id}")
  public Todo getById(@PathVariable Integer id) {
    Optional<Todo> todo = repo.findById(id);
    if (todo.isPresent()) {
      return todo.get();
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found");
    }
  }

  @PostMapping
  public Todo create(@Valid @RequestBody Todo todo) {
    try {
      return repo.save(todo);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to save todo");
    }
  }

  @DeleteMapping
  public String delete() {
    repo.deleteAll();
    return "All todos deleted";
  }

  @DeleteMapping("/{id}")
  public String deleteById(@PathVariable Integer id) {
    Optional<Todo> todoToDelete = repo.findById(id);
    if (todoToDelete.isPresent()) {
      // lo cancello
      repo.deleteById(id);
      return "Todo with id " + id + " deleted";
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found");
    }
  }

  @PutMapping("/{id}")
  public Todo update(@PathVariable Integer id, @Valid @RequestBody Todo todo) {
    Optional<Todo> todoToUpdate = repo.findById(id);
    if (todoToUpdate.isPresent()) {
      // lo modifico
      todo.setId(id);
      return repo.save(todo);
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found");
    }
  }

  @GetMapping("/{id}/toggle")
  public Todo toggleCompleted(@PathVariable Integer id) {
    Optional<Todo> todoToToggle = repo.findById(id);
    if (todoToToggle.isPresent()) {
      // toggle completed
      Todo todo = todoToToggle.get();
      // if(todo.getCompleted()) {
      // todo.setCompleted(false);
      // } else {
      // todo.setCompleted(true);
      // }

      todo.setCompleted(!todo.getCompleted());
      return repo.save(todo);

    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found");
    }
  }

}
