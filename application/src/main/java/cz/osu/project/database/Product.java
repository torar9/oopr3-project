package cz.osu.project.database;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity(name = "Product")
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    private String name;
    private String description;

    @OneToMany(mappedBy="product")
    private Set<StockItem> stockItems;

    public Product(int id, String name, String description, Set<StockItem> stockItems) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.stockItems = stockItems;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<StockItem> getStockItems() {
        return stockItems;
    }

    public void setStockItems(Set<StockItem> stockItems) {
        this.stockItems = stockItems;
    }
}
