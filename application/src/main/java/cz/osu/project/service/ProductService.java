package cz.osu.project.service;

import cz.osu.project.database.entity.Product;
import cz.osu.project.database.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepo;

    public Product create(String name, String description, String manufacturer) throws Exception {
        if(name == null || name.isEmpty())
            throw new Exception("Jméno musí být vyplněno");
        if(description == null || description.isEmpty())
            throw new Exception("Popis musí být vyplněn");
        if(manufacturer == null || manufacturer.isEmpty())
            throw new Exception("Výrobce musí být vyplněn");

        Product product = new Product(name, description, manufacturer);

        return productRepo.save(product);
    }

    public void save(Product product) throws Exception
    {
        if(product.getName() == null || product.getName().isEmpty())
            throw new Exception("Jméno musí být vyplněno");
        if(product.getDescription() == null || product.getDescription().isEmpty())
            throw new Exception("Popis musí být vyplněn");
        if(product.getManufacturer() == null || product.getManufacturer().isEmpty())
            throw new Exception("Výrobce musí být vyplněn");

        productRepo.save(product);
    }

    public Product get(long id) {
        return productRepo.findById(id).orElseThrow(() -> new InvalidParameterException());
    }

    public void delete(long id) {
        Product product = productRepo.findById(id).orElseThrow(() -> new InvalidParameterException("Nelze odstranit"));
        productRepo.delete(product);
    }

    public List<Product> getAll() {
        return productRepo.findAll();
    }
}
