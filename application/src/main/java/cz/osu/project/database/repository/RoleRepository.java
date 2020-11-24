package cz.osu.project.database.repository;

import cz.osu.project.database.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}