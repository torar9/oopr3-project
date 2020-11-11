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

    public void create(String name, String description, String manufacturer) {
        if (name == null || description == null)
            throw new NullPointerException();

        Product product = new Product(name, description, manufacturer);

        productRepo.save(product);
    }

    public Product get(long id) {
        return productRepo.findById(id).orElseThrow(() -> new InvalidParameterException());
    }

    public void delete(long id) {
        Product product = productRepo.findById(id).orElseThrow(() -> new InvalidParameterException());
        productRepo.delete(product);
    }

    public List<Product> getAll() {
        return productRepo.findAll();
    }
}
