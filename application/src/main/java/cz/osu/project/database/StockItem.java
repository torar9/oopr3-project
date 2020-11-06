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
    private int id;
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
}