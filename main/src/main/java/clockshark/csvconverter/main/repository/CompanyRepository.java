package clockshark.csvconverter.main.repository;

import clockshark.csvconverter.main.model.Company;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository

@Qualifier("companyRepository")
public interface CompanyRepository extends MongoRepository<Company, String> {
}
