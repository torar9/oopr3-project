package cz.osu.project.database.repository;

import cz.osu.project.database.entity.StockItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockItemRepository extends JpaRepository<StockItem, Long> {
    @Query("SELECT s FROM StockItem s WHERE s.product.name like %?1%")
    List<StockItem> findByName(@Param("name") String name);

    @Query("SELECT s FROM StockItem s WHERE s.expedition IS NULL")
    List<StockItem> getFreeItems();
}
