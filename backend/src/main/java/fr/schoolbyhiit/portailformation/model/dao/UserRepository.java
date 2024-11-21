package fr.schoolbyhiit.portailformation.model.dao;

import fr.schoolbyhiit.portailformation.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
