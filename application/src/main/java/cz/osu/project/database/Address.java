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

    public Address(int id, String streetName, String buildingNumber, String postalCode, String city, String state, Company company) {
        this.id = id;
        this.streetName = streetName;
        this.buildingNumber = buildingNumber;
        this.postalCode = postalCode;
        this.city = city;
        this.state = state;
        this.company = company;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(String buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
