package cz.osu.project.database.entity;

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
    private Integer quantity;
    private Double price;
    private Double weight;
    @CreationTimestamp
    private LocalDateTime storageDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="product", nullable=false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="expedition", nullable=true)
    private Expedition expedition;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="company", nullable=false)
    private Company company;

    public StockItem() {
    }

    public StockItem(int quantity, Double price, Double weight, Product product, Company company, Expedition expedition) {
        this.quantity = quantity;
        this.price = price;
        this.product = product;
        this.company = company;
        this.weight = weight;
        this.expedition = expedition;
    }

    public StockItem(long id, int quantity, Double price, Double weight, LocalDateTime storageDate, Product product, Expedition expedition, Company company) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
        this.storageDate = storageDate;
        this.product = product;
        this.expedition = expedition;
        this.company = company;
        this.weight = weight;
    }

    public void set(Expedition expedition, Company suplier, Product product, int quantity, Double price, Double weight)
    {
        setExpedition(expedition);
        setCompany(suplier);
        setProduct(product);
        setQuantity(quantity);
        setPrice(price);
        setWeight(weight);
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
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

    @Override
    public String toString() {
        return "StockItem{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", price=" + price +
                ", storageDate=" + storageDate +
                ", product=" + product +
                ", expedition=" + expedition +
                ", company=" + company +
                ", weight=" + weight +
                '}';
    }
}