package jana60.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

}
