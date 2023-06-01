package clockshark.csvconverter.main.test;

import clockshark.csvconverter.main.repository.CompanyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class CompanyRepositoryTest {

    @Autowired
    private CompanyRepository companyRepository;

    @Test
    public void testCompanyRepositoryBeanCreation() {
        assertNotNull(companyRepository, "CompanyRepository bean should not be null");
    }
}
