package cz.osu.project.service;

import cz.osu.project.database.entity.Address;
import cz.osu.project.database.entity.Company;
import cz.osu.project.database.entity.Product;
import cz.osu.project.database.entity.StockItem;
import cz.osu.project.database.repository.StockItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;

@Service
public class StockItemService {
    @Autowired
    StockItemRepository stockItemRepo;
    @Autowired
    ProductService productService;
    @Autowired
    CompanyService companyService;

    public void create(int quantity, Double price, long productID, long companyID) {
        if (price == null)
            throw new NullPointerException();

        Product product = productService.get(productID);
        Company comp = companyService.get(companyID);

        StockItem item = new StockItem(quantity, price, product, comp);

        stockItemRepo.save(item);
    }

    public StockItem get(long id) {
        return stockItemRepo.findById(id).orElseThrow(() -> new InvalidParameterException());
    }

    public void delete(long id)
    {
        StockItem item = stockItemRepo.findById(id).orElseThrow(() -> new InvalidParameterException());
        stockItemRepo.delete(item);
    }

    public List<StockItem> getAll()
    {
        return stockItemRepo.findAll();
    }
}
