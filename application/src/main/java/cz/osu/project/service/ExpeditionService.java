package cz.osu.project.service;

import cz.osu.project.database.entity.Address;
import cz.osu.project.database.entity.Company;
import cz.osu.project.database.entity.Expedition;
import cz.osu.project.database.repository.ExpeditionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;

@Service
public class ExpeditionService {
    @Autowired
    ExpeditionRepository expoRepo;
    @Autowired
    CompanyService companyService;
    @Autowired
    StockItemService stockItemService;

    public void create(long companyID, String status) {
        if (status == null)
            throw new NullPointerException();

        Company company = companyService.get(companyID);

        Expedition expedition = new Expedition(status, company);

        expoRepo.save(expedition);
    }

    public Expedition get(long id) {
        return expoRepo.findById(id).orElseThrow(() -> new InvalidParameterException());
    }

    public void delete(long id)
    {
        Expedition expedition = expoRepo.findById(id).orElseThrow(() -> new InvalidParameterException());
        expoRepo.delete(expedition);
    }

    public List<Expedition> getAll()
    {
        return expoRepo.findAll();
    }
}
