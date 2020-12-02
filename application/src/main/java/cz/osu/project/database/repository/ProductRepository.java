package cz.osu.project.database.repository;

import cz.osu.project.database.entity.Product;
import cz.osu.project.database.entity.StockItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE p.name like %?1%")
    List<Product> findByName(@Param("name") String name);
}
