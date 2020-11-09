package cz.osu.project.database;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "Company")
@Table(name = "company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address", referencedColumnName = "id")
    private Address address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contact", referencedColumnName = "id")
    private Contact contact;

    @OneToMany(mappedBy = "company")
    private Set<Expedition> expeditions;

    public Company(String name, Address address, Contact contact) {
        this.name = name;
        this.address = address;
        this.contact = contact;
    }

    public Company(int id, String name, Address address, Contact contact, Set<Expedition> expeditions) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.expeditions = expeditions;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Set<Expedition> getExpeditions() {
        return expeditions;
    }

    public void setExpeditions(Set<Expedition> expeditions) {
        this.expeditions = expeditions;
    }
}
