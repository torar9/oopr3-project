package cz.osu.project.database.entity;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "Contact")
@Table(name = "contact")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    private String email;
    private String phone;
    private String fax;

    @OneToMany(mappedBy="contact")
    @Column(nullable = true)
    private Set<Company> companies;

    public Contact() {
    }

    public Contact(long id, String email, String phone, String fax, Set<Company> companies) {
        this.id = id;
        this.email = email;
        this.phone = phone;
        this.fax = fax;
        this.companies = companies;
    }

    public Contact(long id, String email, String phone, String fax) {
        this.id = id;
        this.email = email;
        this.phone = phone;
        this.fax = fax;
    }

    public Contact(String email, String phone, String fax) {
        this.email = email;
        this.phone = phone;
        this.fax = fax;
    }

    public void set(String email, String phone, String fax)
    {
        setEmail(email);
        setPhone(phone);
        setFax(fax);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public Set<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(Set<Company> companies) {
        this.companies = companies;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", fax='" + fax + '\'' +
                '}';
    }
}
