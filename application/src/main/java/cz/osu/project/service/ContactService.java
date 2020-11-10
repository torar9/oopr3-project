package cz.osu.project.service;

import cz.osu.project.database.entity.Address;
import cz.osu.project.database.entity.Contact;
import cz.osu.project.database.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;

@Service
public class ContactService {
    @Autowired
    ContactRepository contactRepo;

    public void create(String email, String phone, String fax) {
        if (email == null || phone == null)
            throw new NullPointerException();

        Contact contact = new Contact(email, phone, fax);

        contactRepo.save(contact);
    }

    public Contact get(long id) {
        return contactRepo.findById(id).orElseThrow(() -> new InvalidParameterException());
    }

    public void delete(long id) {
        Contact contact = contactRepo.findById(id).orElseThrow(() -> new InvalidParameterException());
        contactRepo.delete(contact);
    }

    public List<Contact> getAll() {
        return contactRepo.findAll();
    }
}
