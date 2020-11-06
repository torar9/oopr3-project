package cz.osu.project.database;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Address")
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    private String streetName;
    private String buildingNumber;
    private String postalCode;
    private String city;
    private String state;

    @OneToOne(mappedBy = "address")
    private Company company;
}
