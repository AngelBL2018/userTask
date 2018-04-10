package usertask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import usertask.model.User;
import usertask.repositroy.UserRepository;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/user")
    public ResponseEntity addUser(@ModelAttribute User user) {
        User user1 = userRepository.findUserByEmail(user.getEmail());
        if (user1 == null) {
            userRepository.save(user);
            return ResponseEntity.ok("User added successfully");
        }
        return ResponseEntity.badRequest().body(String.format("User with %s email already exists", user.getEmail()));
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    @GetMapping("/users/{id}")
    public ResponseEntity getUserById(@PathVariable("id") int id) {
        User user = userRepository.findUserById(id);
        if (user == null) {
            return ResponseEntity.badRequest().body(String.format("User with %s id not found", id));
        }
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity deleteUserById(@PathVariable("id") int id) {

        User user = userRepository.findUserById(id);
        if (user == null) {
            return ResponseEntity.badRequest().body(String.format("User with %s id not found", id));
        }
        userRepository.delete(id);
        return ResponseEntity.ok("User deleted successfully");
    }

    @PutMapping("/user")
    public ResponseEntity changeUser(@ModelAttribute User user) {
        User userExists = userRepository.findUserById(user.getId());
        if (userExists == null) {
            return ResponseEntity.badRequest().body(String.format("User with %s id not found", user.getId()));
        }
        userRepository.save(user);
        return ResponseEntity.ok("User changed successfully");
    }


}
