package espm.account;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import espm.account.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}