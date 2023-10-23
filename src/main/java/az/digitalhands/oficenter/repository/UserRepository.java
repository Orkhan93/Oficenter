package az.digitalhands.oficenter.repository;

import az.digitalhands.oficenter.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailEqualsIgnoreCase(String email);
    Optional<User> findByUsernameEqualsIgnoreCase(String username);

}