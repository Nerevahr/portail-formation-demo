package fr.schoolbyhiit.portailformation.model.dao;

import fr.schoolbyhiit.portailformation.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
