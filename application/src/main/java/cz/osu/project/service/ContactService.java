package cz.osu.project.service;

import cz.osu.project.database.entity.Contact;
import cz.osu.project.database.repository.ContactRepository;
import cz.osu.project.exception.UserErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;

@Service
public class ContactService {
    @Autowired
    ContactRepository contactRepo;

    public Contact create(String email, String phone, String fax) throws UserErrorException {
        checkMandatoryFields(email, phone);

        Contact contact = new Contact(email, phone, fax);

        return contactRepo.save(contact);
    }

    public void save(Contact contact) throws UserErrorException {
        checkMandatoryFields(contact);

        contactRepo.save(contact);
    }

    public Contact get(Long id) {
        return contactRepo.findById(id).orElseThrow(() -> new InvalidParameterException());
    }

    public void delete(Long id) {
        Contact contact = contactRepo.findById(id).orElseThrow(() -> new InvalidParameterException("Nelze odstranit"));
        contactRepo.delete(contact);
    }

    public List<Contact> getAll() {
        return contactRepo.findAll();
    }

    private void checkMandatoryFields(String email, String phone) throws UserErrorException {
        if(email == null || email.isEmpty())
            throw new UserErrorException("E-mail musí být vyplněn");
        if(phone == null || phone.isEmpty())
            throw new UserErrorException("Telefonní kontakt musí být vyplněn");
    }

    private void checkMandatoryFields(Contact contact) throws UserErrorException {
        this.checkMandatoryFields(contact.getEmail(), contact.getPhone());
    }
}
