package cz.osu.project.service;

import cz.osu.project.database.entity.Address;
import cz.osu.project.database.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;

@Service
public class AddressService {
    @Autowired
    AddressRepository addressRepo;

    public Address create(String streetName, String buildingNumber, String postalCode, String city, String state) {
        if (streetName == null || buildingNumber == null || postalCode == null || city == null || state == null)
            throw new NullPointerException();

        Address address = new Address(streetName, buildingNumber, postalCode, city, state);

        return addressRepo.save(address);
    }

    public void save(Address address)
    {
        addressRepo.save(address);
    }

    public Address get(long id) {
        return addressRepo.findById(id).orElseThrow(() -> new InvalidParameterException());
    }

    public void delete(long id)
    {
        Address address = addressRepo.findById(id).orElseThrow(() -> new InvalidParameterException("Nelze odstranit"));
        addressRepo.delete(address);
    }

    public List<Address> getAll()
    {
        return addressRepo.findAll();
    }
}
