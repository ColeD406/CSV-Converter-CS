package clockshark.csvconverter.main.database;

import clockshark.csvconverter.main.model.Company;
import clockshark.csvconverter.main.repository.CompanyRepository;
import clockshark.csvconverter.main.service.Email;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("dbAccess")
public class DBAccess {
    private static final Logger logger = LoggerFactory.getLogger(DBAccess.class);

    private final CompanyRepository companyRepository;
    private final Email emailService;

    @Autowired
    public DBAccess(@Qualifier("companyRepository") CompanyRepository companyRepository, Email emailService) {
        this.companyRepository = companyRepository;
        this.emailService = emailService;
    }


    public void saveCompanyData(String companyId, String attributes, String email, String payComponent) {
        Company company = new Company(companyId, attributes, email, payComponent);
        try {
            companyRepository.save(company);
        } catch (Throwable e) {
            logger.error(e, () -> "Error saving CSV file: " + e.getMessage());
        }
    }

    public void deleteCompanyData(String companyId) {
        try {
            companyRepository.deleteById(companyId);
        }
        catch (Throwable e) {
            logger.error(e, () -> "Error Deleting CompanyId: " + e.getMessage());
        }
    }
    public Company savedCompanyTemplate(String companyId) {
        Optional<Company> optionalCompany = companyRepository.findById(companyId);
        return optionalCompany.orElse(null);
    }
}
