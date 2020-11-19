package cz.osu.project.service;

import cz.osu.project.database.entity.Address;
import cz.osu.project.database.entity.Company;
import cz.osu.project.database.entity.Contact;
import cz.osu.project.database.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;

@Service
public class CompanyService {
    @Autowired
    CompanyRepository companyRepo;
    @Autowired
    AddressService addressService;
    @Autowired
    ContactService contactService;

    public Company create(String name, Long addressID, Long contactID) {
        if (name == null)
            throw new NullPointerException();

        Address address = addressService.get(addressID);
        Contact contact = contactService.get(contactID);

        Company item = new Company(name, address, contact);

        return companyRepo.save(item);
    }

    public void save(Company company)
    {
        companyRepo.save(company);
    }

    public Company get(Long id) {
        return companyRepo.findById(id).orElseThrow(() -> new InvalidParameterException());
    }

    public void delete(Long id) {
        Company item = companyRepo.findById(id).orElseThrow(() -> new InvalidParameterException("Nelze odstranit"));
        companyRepo.delete(item);
    }

    public List<Company> getAll() {
        return companyRepo.findAll();
    }
}
