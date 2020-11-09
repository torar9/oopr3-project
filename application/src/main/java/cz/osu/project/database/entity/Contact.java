package cz.osu.project.database;

import org.springframework.lang.Nullable;

import javax.persistence.*;

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

    @OneToOne(mappedBy = "contact")
    private Company company;

    public Contact(long id, String email, String phone, String fax, Company company) {
        this.id = id;
        this.email = email;
        this.phone = phone;
        this.fax = fax;
        this.company = company;
    }

    public Contact(String email, String phone, String fax) {
        this.email = email;
        this.phone = phone;
        this.fax = fax;
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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
