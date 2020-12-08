package cz.osu.project.service;

import cz.osu.project.database.entity.Product;
import cz.osu.project.database.repository.ProductRepository;
import cz.osu.project.exception.UserErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepo;

    public Product create(String name, String description, String manufacturer) throws UserErrorException {
        checkMandatoryFields(name, description, manufacturer);

        Product product = new Product(name, description, manufacturer);

        return productRepo.save(product);
    }

    public void save(Product product) throws UserErrorException {
        checkMandatoryFields(product);

        productRepo.save(product);
    }

    public Product get(Long id) {
        return productRepo.findById(id).orElseThrow(() -> new InvalidParameterException());
    }

    public void delete(Long id) throws UserErrorException {
        Product product = productRepo.findById(id).orElseThrow(() -> new InvalidParameterException("Nelze odstranit"));
        if(product.getStockItems().size() > 0)
            throw new UserErrorException("Produkt je používán");
        productRepo.delete(product);
    }

    public List<Product> getAll() {
        return productRepo.findAll();
    }

    public List<Product> searchByName(String name) {
        return productRepo.findByName(name);
    }

    private void checkMandatoryFields(String name, String description, String manufacturer) throws UserErrorException {
        if(name == null || name.isEmpty())
            throw new UserErrorException("Jméno musí být vyplněno");
        if(description == null || description.isEmpty())
            throw new UserErrorException("Popis musí být vyplněn");
        if(manufacturer == null || manufacturer.isEmpty())
            throw new UserErrorException("Výrobce musí být vyplněn");
    }

    private void checkMandatoryFields(Product product) throws UserErrorException {
        this.checkMandatoryFields(product.getName(), product.getDescription(), product.getManufacturer());
    }
}
