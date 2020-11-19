package cz.osu.project.database.entity;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "Company")
@Table(name = "company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="address", nullable=false)
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="contact", nullable=false)
    private Contact contact;

    @OneToMany(mappedBy = "company")
    @Column(nullable = true)
    private Set<Expedition> expeditions;

    public Company() {
    }

    public Company(String name, Address address, Contact contact) {
        this.name = name;
        this.address = address;
        this.contact = contact;
    }

    public Company(Long id, String name, Address address, Contact contact, Set<Expedition> expeditions) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.expeditions = expeditions;
    }

    public void set(String name, Address address, Contact contact)
    {
        setName(name);
        this.address = address;
        this.contact = contact;
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

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address=" + address +
                ", contact=" + contact +
                ", expeditions=" + expeditions +
                '}';
    }
}
