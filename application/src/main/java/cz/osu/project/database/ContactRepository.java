package cz.osu.project.database;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@ComponentScan({"cz.osu.project.service"})
public interface ContactRepository extends JpaRepository<Contact, Long> {
}
