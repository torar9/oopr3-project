package cz.osu.project.service;

import cz.osu.project.database.entity.Company;
import cz.osu.project.database.entity.Contact;
import cz.osu.project.database.entity.Expedition;
import cz.osu.project.database.repository.ExpeditionRepository;
import cz.osu.project.exception.UserErrorException;
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

    public Expedition create(String status, Long companyID) throws UserErrorException {
        checkMandatoryFields(status, companyID);

        Company company = companyService.get(companyID);

        Expedition expedition = new Expedition(status, company);

        return expoRepo.save(expedition);
    }

    public void save(Expedition expedition) throws UserErrorException {
        checkMandatoryFields(expedition);

        expoRepo.save(expedition);
    }

    public Expedition get(Long id) {
        return expoRepo.findById(id).orElseThrow(() -> new InvalidParameterException());
    }

    public void delete(Long id) {
        Expedition expedition = expoRepo.findById(id).orElseThrow(() -> new InvalidParameterException("Nelze odstranit"));
        expoRepo.delete(expedition);
    }

    public void storno(Long id) {
        Expedition expedition = expoRepo.findById(id).orElseThrow(() -> new InvalidParameterException("Nelze odstranit"));
        expedition.setStatus("Storno");
        expoRepo.save(expedition);
    }

    public List<Expedition> getAll()
    {
        return expoRepo.findAll();
    }

    private void checkMandatoryFields(String status, Long companyID) throws UserErrorException {
        if(status == null || status.isEmpty())
            throw new UserErrorException("Status musí být vyplněn");
        if(companyID == null)
            throw new UserErrorException("Odběratel musí být vyplněn");
    }

    private void checkMandatoryFields(Expedition expedition) throws UserErrorException {
        if(expedition.getStatus() == null || expedition.getStatus().isEmpty())
            throw new UserErrorException("Status musí být vyplněn");
        if(expedition.getCompany() == null)
            throw new UserErrorException("Odběratel musí být vyplněn");
    }
}
