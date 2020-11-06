package cz.osu.project.database;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity(name = "Expedition")
@Table(name = "expedition")
public class Expedition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    private String status;
    @CreationTimestamp
    private LocalDateTime createDate;

    @OneToMany(mappedBy = "expedition")
    private Set<StockItem> stockItems;

    @ManyToOne
    @JoinColumn(name="company", nullable=false)
    private Company company;
}
