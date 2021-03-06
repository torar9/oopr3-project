package cz.osu.project.service;

import cz.osu.project.database.entity.Address;
import cz.osu.project.database.entity.Company;
import cz.osu.project.database.entity.Contact;
import cz.osu.project.database.repository.CompanyRepository;
import cz.osu.project.exception.NotFoundException;
import cz.osu.project.exception.UserErrorException;
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

    public Company create(String name, Long addressID, Long contactID) throws UserErrorException {
        checkMandatoryFields(name, addressID, contactID);

        Address address = addressService.get(addressID);
        Contact contact = contactService.get(contactID);

        Company item = new Company(name, address, contact);

        return companyRepo.save(item);
    }

    public Company save(Company company) throws UserErrorException {
        checkMandatoryFields(company);

        return companyRepo.save(company);
    }

    public Company get(Long id) throws NotFoundException {
        return companyRepo.findById(id).orElseThrow(() -> new NotFoundException("Společnost nenalezena"));
    }

    public void delete(Long id) throws UserErrorException, NotFoundException {
        Company company = companyRepo.findById(id).orElseThrow(() -> new NotFoundException("Společnost nenalezena"));

        if(company.getExpeditions().size() > 0)
            throw new UserErrorException("Nelze odstranit, se společností jsou spojené záznamy");

        companyRepo.delete(company);
    }

    public List<Company> getAll() {
        return companyRepo.findAll();
    }

    public List<Company> searchByName(String name) {
        return companyRepo.findByName(name);
    }

    private void checkMandatoryFields(String name, Long addressID, Long contactID) throws UserErrorException {
        if(name == null || name.isEmpty())
            throw new UserErrorException("Název společnosti musí být vyplněn");
        if(addressID == null)
            throw new UserErrorException("Adresa musí být vyplněna");
        if(contactID == null)
            throw new UserErrorException("Kontakt musí být vyplněn");
    }

    private void checkMandatoryFields(Company company) throws UserErrorException {
        if(company.getName() == null || company.getName().isEmpty())
            throw new UserErrorException("Název společnosti musí být vyplněn");
        if(company.getAddress() == null)
            throw new UserErrorException("Adresa musí být vyplněna");
        if(company.getContact() == null)
            throw new UserErrorException("Kontakt musí být vyplněn");
    }
}
