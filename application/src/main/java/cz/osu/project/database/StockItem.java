package cz.osu.project.database;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "StockItem")
@Table(name = "stockItem")
public class StockItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    private int quantity;
    private Double price;
    @CreationTimestamp
    private LocalDateTime storageDate;

    @ManyToOne
    @JoinColumn(name="product", nullable=false)
    private Product product;

    @ManyToOne
    @JoinColumn(name="expedition", nullable=false)
    private Expedition expedition;

    @ManyToOne
    @JoinColumn(name="company", nullable=false)
    private Company company;

    public StockItem(long id, int quantity, Double price, LocalDateTime storageDate, Product product, Expedition expedition, Company company) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
        this.storageDate = storageDate;
        this.product = product;
        this.expedition = expedition;
        this.company = company;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDateTime getStorageDate() {
        return storageDate;
    }

    public void setStorageDate(LocalDateTime storageDate) {
        this.storageDate = storageDate;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Expedition getExpedition() {
        return expedition;
    }

    public void setExpedition(Expedition expedition) {
        this.expedition = expedition;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}