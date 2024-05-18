package CodeWithSecurity.Spring.jwt.Repository;

import CodeWithSecurity.Spring.jwt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer>
{
         Optional<User> findByUsername(String username);
}
