package usertask.repositroy;

import org.springframework.data.jpa.repository.JpaRepository;
import usertask.model.User;

public interface UserRepository extends JpaRepository<User,Integer> {

    User findUserByEmail(String email);
    User findUserById(int id);

}
