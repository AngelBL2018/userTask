package usertask.repositroy;

import org.springframework.data.jpa.repository.JpaRepository;
import usertask.model.Task;

import java.util.Date;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Integer> {

    Task getTaskById(int id);
   List<Task> findTasksByUserId(int id);
   List<Task> findTasksByDate(String date);
}
