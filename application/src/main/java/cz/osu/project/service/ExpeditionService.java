package cz.osu.project.service;

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

    public Expedition create(Long companyID, String status) {
        if (status == null)
            throw new NullPointerException();

        Company company = companyService.get(companyID);

        Expedition expedition = new Expedition(status, company);

        return expoRepo.save(expedition);
    }

    public void save(Expedition expedition)
    {
        expoRepo.save(expedition);
    }

    public Expedition get(Long id) {
        return expoRepo.findById(id).orElseThrow(() -> new InvalidParameterException());
    }

    public void delete(Long id) {
        Expedition expedition = expoRepo.findById(id).orElseThrow(() -> new InvalidParameterException("Nelze odstranit"));
        expoRepo.delete(expedition);
    }

    public List<Expedition> getAll()
    {
        return expoRepo.findAll();
    }
}
