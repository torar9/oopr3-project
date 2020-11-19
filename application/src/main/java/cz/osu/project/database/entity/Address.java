package cz.osu.project.database.entity;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "Address")
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String streetName;
    private String buildingNumber;
    private String postalCode;
    private String city;
    private String state;

    @OneToMany(mappedBy="address")
    @Column(nullable = true)
    private Set<Company> companies;

    public Address() {
    }

    public Address(long id, String streetName, String buildingNumber, String postalCode, String city, String state, Set<Company> companies) {
        this.id = id;
        this.streetName = streetName;
        this.buildingNumber = buildingNumber;
        this.postalCode = postalCode;
        this.city = city;
        this.state = state;
        this.companies = companies;
    }

    public Address(String streetName, String buildingNumber, String postalCode, String city, String state) {
        this.streetName = streetName;
        this.buildingNumber = buildingNumber;
        this.postalCode = postalCode;
        this.city = city;
        this.state = state;
    }

    public Address(long id, String streetName, String buildingNumber, String postalCode, String city, String state) {
        this.id = id;
        this.streetName = streetName;
        this.buildingNumber = buildingNumber;
        this.postalCode = postalCode;
        this.city = city;
        this.state = state;
    }

    public void set(String streetName, String buildingNumber, String postalCode, String city, String state)
    {
        setStreetName(streetName);
        setBuildingNumber(buildingNumber);
        setPostalCode(postalCode);
        setCity(city);
        setState(state);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Set<Company> getAddresses() {
        return companies;
    }

    public void setAddresses(Set<Company> addresses) {
        this.companies = companies;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", streetName='" + streetName + '\'' +
                ", buildingNumber='" + buildingNumber + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
