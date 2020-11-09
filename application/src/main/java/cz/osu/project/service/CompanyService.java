package cz.osu.project.service;

import cz.osu.project.database.entity.Address;
import cz.osu.project.database.entity.Company;
import cz.osu.project.database.entity.Contact;
import cz.osu.project.database.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;

@Service
public class CompanyService {
    @Autowired
    CompanyRepository companyRepo;
    @Autowired
    AddressService addressService;
    @Autowired
    ContactService contactService;

    public void create(String name, long addressID, long contactID) {
        if (name == null)
            throw new NullPointerException();

        Address address = addressService.get(addressID);
        Contact contact = contactService.get(contactID);

        Company item = new Company(name, address, contact);

        companyRepo.save(item);
    }

    public Company get(long id) {
        return companyRepo.findById(id).orElseThrow(() -> new InvalidParameterException());
    }

    public void delete(long id)
    {
        Company item = companyRepo.findById(id).orElseThrow(() -> new InvalidParameterException());
        companyRepo.delete(item);
    }
}
