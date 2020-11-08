package cz.osu.project.service;

import cz.osu.project.database.Product;
import cz.osu.project.database.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepo;

    public void create(String name, String description) {
        if (name == null || description == null)
            throw new NullPointerException();

        Product product = new Product(name, description);

        productRepo.save(product);
    }

    public void delete(long id)
    {
        Product product = productRepo.findById(id).orElseThrow(() -> new InvalidParameterException());
        productRepo.delete(product);
    }
}
