package jana60.repository;

import org.springframework.data.repository.CrudRepository;
import jana60.model.Todo;

public interface TodoRepository extends CrudRepository<Todo, Integer> {

}
