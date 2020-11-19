package cz.osu.project.service;

import cz.osu.project.database.entity.Company;
import cz.osu.project.database.entity.Expedition;
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
    @Autowired
    ExpeditionService expeditionService;

    public StockItem create(Integer quantity, Double price, Double weight, Long productID, Long companyID, Long expeditionID) throws Exception {
        if(price == null)
            throw new Exception("Neplatná cena");
        if(weight == null)
            throw new Exception("Neplatná váha");

        Product product = productService.get(productID);
        Company comp = companyService.get(companyID);
        Expedition expedition = null;
        if(expeditionID != null)
            expedition = expeditionService.get(expeditionID);
        StockItem item = new StockItem(quantity, price, weight, product, comp, expedition);

        return stockItemRepo.save(item);
    }

    public void save(StockItem stockItem) throws Exception
    {
        if(stockItem.getPrice() == null)
            throw new Exception("Neplatná cena");
        if(stockItem.getWeight() == null)
            throw new Exception("Neplatná váha");

        stockItemRepo.save(stockItem);
    }

    public StockItem get(Long id) {
        return stockItemRepo.findById(id).orElseThrow(() -> new InvalidParameterException());
    }

    public void delete(Long id) {
        StockItem item = stockItemRepo.findById(id).orElseThrow(() -> new InvalidParameterException("Nelze odstranit"));
        stockItemRepo.delete(item);
    }

    public List<StockItem> getAll() {
        return stockItemRepo.findAll();
    }
}
