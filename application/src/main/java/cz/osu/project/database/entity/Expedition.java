package cz.osu.project.database.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity(name = "Expedition")
@Table(name = "expedition")
public class Expedition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    private String status;
    @CreationTimestamp
    private LocalDateTime createDate;

    @OneToMany(mappedBy = "expedition")
    private Set<StockItem> stockItems;

    @ManyToOne
    @JoinColumn(name="company", nullable=false)
    private Company company;

    public Expedition() {
    }

    public Expedition(String status, Company company) {
        this.status = status;
        this.company = company;
    }

    public Expedition(String status, Set<StockItem> stockItems, Company company) {
        this.status = status;
        this.stockItems = stockItems;
        this.company = company;
    }

    public Expedition(long id, String status, LocalDateTime createDate, Set<StockItem> stockItems, Company company) {
        this.id = id;
        this.status = status;
        this.createDate = createDate;
        this.stockItems = stockItems;
        this.company = company;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public Set<StockItem> getStockItems() {
        return stockItems;
    }

    public void setStockItems(Set<StockItem> stockItems) {
        this.stockItems = stockItems;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "Expedition{" +
                "id=" + id +
                ", status='" + status + '\'' +
                ", createDate=" + createDate +
                ", stockItems=" + stockItems +
                '}';
    }
}
