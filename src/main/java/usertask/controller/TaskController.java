package usertask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import usertask.model.Task;
import usertask.model.User;
import usertask.repositroy.TaskRepository;
import usertask.repositroy.UserRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;


    @GetMapping("/tasks")
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @GetMapping("/task/{id}")
    public ResponseEntity getTaskById(@PathVariable("id") int id) {
        Task task = taskRepository.getTaskById(id);
        if (task == null) {
            return ResponseEntity.badRequest().body(String.format("Task with %s id not found", id));
        }
        return ResponseEntity.ok(task);
    }

    @GetMapping("tasks/byUserId/{userId}")
    public ResponseEntity getTasksByUserId(@PathVariable("userId") int userId) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            return ResponseEntity.badRequest().body(String.format("User with %s id not found", userId));
        }
        return ResponseEntity.ok(taskRepository.findTasksByUserId(userId));
    }


    @PutMapping("/task")
    public ResponseEntity changeTask(@ModelAttribute Task task) {
        Task taskExist = taskRepository.findOne(task.getId());
        if (taskExist == null) {
            return ResponseEntity.badRequest().body(String.format("Task with %s id not found", task.getId()));
        }
        taskRepository.save(task);
        return ResponseEntity.ok("Task changed successfully");

    }


    @DeleteMapping("/task/{taskId}")
    public ResponseEntity deleteTask(@PathVariable("taskId") int taskId) {
        Task taskExist = taskRepository.findOne(taskId);
        if (taskExist == null) {
            return ResponseEntity.badRequest().body(String.format("Task with %s id not found", taskId));
        }
        taskRepository.delete(taskId);
        return ResponseEntity.ok("Task deleted successfully");

    }

    @PostMapping("/task")
    public ResponseEntity addTask(@ModelAttribute Task task) {
        taskRepository.save(task);
        return ResponseEntity.ok("Task added successfully");
    }


    @GetMapping("/tasks/byStartDate")
    public List<Task> getTasksByDate(@RequestParam("date") String date) throws ParseException {


        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
        Date dateParse = dt.parse(date);
        String dateFormat = dt.format(dateParse);
        return taskRepository.findTasksByDate(dateFormat);


    }
}
