package cz.osu.project.service;

import cz.osu.project.database.entity.Address;
import cz.osu.project.database.repository.AddressRepository;
import cz.osu.project.exception.UserErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;

@Service
public class AddressService {
    @Autowired
    AddressRepository addressRepo;

    public Address create(String streetName, String buildingNumber, String postalCode, String city, String state) throws UserErrorException {
        checkMandatoryFields(streetName, buildingNumber, postalCode, city, state);
        Address address = new Address(streetName, buildingNumber, postalCode, city, state);

        return addressRepo.save(address);
    }

    public void save(Address address) throws UserErrorException {
        checkMandatoryFields(address);
        addressRepo.save(address);
    }

    public Address get(long id) {
        return addressRepo.findById(id).orElseThrow(() -> new InvalidParameterException());
    }

    public void delete(long id) throws UserErrorException {
        Address address = addressRepo.findById(id).orElseThrow(() -> new InvalidParameterException("Nelze odstranit"));
        if(address.getAddresses().size() > 0)
            throw new UserErrorException("Adresa je používána");
        addressRepo.delete(address);
    }

    public List<Address> getAll()
    {
        return addressRepo.findAll();
    }

    private void checkMandatoryFields(String streetName, String buildingNumber, String postalCode, String city, String state) throws UserErrorException {
        if(streetName == null || streetName.isEmpty())
            throw new UserErrorException("Název ulice musí být vyplněn");
        if(buildingNumber == null || buildingNumber.isEmpty())
            throw new UserErrorException("Číslo popísné musí být vyplněno");
        if(postalCode == null || postalCode.isEmpty())
            throw new UserErrorException("PSČ musí být vyplněno");
        if(city == null || city.isEmpty())
            throw new UserErrorException("Název města musí být vyplněn");
        if(state == null || state.isEmpty())
            throw new UserErrorException("Stát musí být vyplněn");
    }

    private void checkMandatoryFields(Address address) throws UserErrorException{
        this.checkMandatoryFields(address.getStreetName(), address.getBuildingNumber(), address.getPostalCode(), address.getCity(), address.getState());
    }
}
