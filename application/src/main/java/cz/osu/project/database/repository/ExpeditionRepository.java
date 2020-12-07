package cz.osu.project.database.repository;

import cz.osu.project.database.entity.Expedition;
import cz.osu.project.database.entity.StockItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ExpeditionRepository extends JpaRepository<Expedition, Long> {
    @Query("SELECT e FROM Expedition e WHERE e.id = ?1")
    List<Expedition> findByID(@Param("id") Long id);

    @Query("SELECT e FROM Expedition e WHERE e.status LIKE ?1")
    List<Expedition> getExpeditionsAccordingToStatus(@Param("status") String status);

    @Query("SELECT e FROM Expedition e WHERE e.company.id = ?1")
    List<Expedition> getCompanyExpeditions(@Param("company") Long company);

    @Query("SELECT e.stockItems FROM Expedition e WHERE e.id = ?1")
    List<StockItem> getItemsInExpedition(@Param("expedition") Long expedition);

    @Query("SELECT e FROM Expedition e WHERE e.status LIKE 'Vytvořeno'")
    List<Expedition> getOngoinExpeditions();
}
