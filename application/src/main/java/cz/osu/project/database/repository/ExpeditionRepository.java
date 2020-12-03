package cz.osu.project.database.repository;

import cz.osu.project.database.entity.Expedition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ExpeditionRepository extends JpaRepository<Expedition, Long> {
    @Query("SELECT e FROM Expedition e WHERE e.id = ?1")
    List<Expedition> findByID(@Param("id") Long id);
}
