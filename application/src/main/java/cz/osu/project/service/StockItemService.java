package cz.osu.project.service;

import cz.osu.project.database.entity.*;
import cz.osu.project.database.repository.StockItemRepository;
import cz.osu.project.exception.UserErrorException;
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

    public StockItem create(Integer quantity, Double price, Double weight, Long productID, Long companyID, Long expeditionID) throws UserErrorException {
        checkMandatoryFields(quantity, price, weight, productID, companyID);

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
        checkMandatoryFields(stockItem);

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

    private void checkMandatoryFields(Integer quantity, Double price, Double weight, Long productID, Long companyID) throws UserErrorException {
        if(quantity == null) {
            throw new UserErrorException("Počet kusů musí být vyplněn");
        }
        else if(quantity <= 0)
            throw new UserErrorException("Neplatně zadaná hodnota počtu kusů. Zadejte kladné číslo");
        if(price == null)
            throw new UserErrorException("Cena musí být vyplněna");
        if(weight == null)
            throw new UserErrorException("Váha musí být vyplněna");
        if(productID == null)
            throw new UserErrorException("Číslo produktu musí být vyplněno");
        if(companyID == null)
            throw new UserErrorException("Dodavatel musí být vyplněn");
    }

    private void checkMandatoryFields(StockItem stockItem) throws UserErrorException {
        if(stockItem.getQuantity() == null) {
            throw new UserErrorException("Počet kusů musí být vyplněn");
        }
        else if(stockItem.getQuantity() <= 0)
            throw new UserErrorException("Neplatně zadaná hodnota počtu kusů. Zadejte kladné číslo");
        if(stockItem.getPrice() == null)
            throw new UserErrorException("Cena musí být vyplněna");
        if(stockItem.getWeight() == null)
            throw new UserErrorException("Váha musí být vyplněna");
        if(stockItem.getProduct() == null)
            throw new UserErrorException("Číslo produktu musí být vyplněno");
        if(stockItem.getCompany() == null)
            throw new UserErrorException("Dodavatel musí být vyplněn");
    }
}
