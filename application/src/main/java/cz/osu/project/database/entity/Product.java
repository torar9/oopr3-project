package cz.osu.project.database.entity;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "Product")
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(unique=true)
    private String name;
    private String description;
    private String manufacturer;

    @OneToMany(mappedBy="product")
    @Column(nullable = true)
    private Set<StockItem> stockItems;

    public Product() {
    }

    public Product(String name, String description, String manufacturer) {
        this.name = name;
        this.description = description;
        this.manufacturer = manufacturer;
    }

    public Product(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Product(long id, String name, String description, String manufacturer, Set<StockItem> stockItems) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.stockItems = stockItems;
    }

    public void set(String name, String description, String manufacturer)
    {
        setName(name);
        setDescription(description);
        setManufacturer(manufacturer);
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
