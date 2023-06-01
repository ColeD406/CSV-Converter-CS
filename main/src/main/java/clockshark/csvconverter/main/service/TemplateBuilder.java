package clockshark.csvconverter.main.service;

import clockshark.csvconverter.main.database.DBAccess;
import jakarta.mail.MessagingException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class TemplateBuilder {
    private static final Logger logger = LoggerFactory.getLogger(TemplateBuilder.class);

    private final DBAccess dbAccess;
    private final Email emailService;


    @Autowired
    public TemplateBuilder(DBAccess dbAccess, Email emailService) {
        this.dbAccess = dbAccess;
        this.emailService = emailService;
    }

    public void createCSV(List<List<String>> inputCSV, String email) throws IOException {
        // Define the output path for the CSV file
        Path outputPath = Paths.get("output.csv");

        // Use try-with-resources to automatically close the PrintWriter and CSVPrinter
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(outputPath));
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {

            // Iterate through the inputCSV and print each row to the CSV file
            for (List<String> row : inputCSV) {
                csvPrinter.printRecord(row);
            }
            csvPrinter.flush();
//            emailService.sendEmailWithAttachment(email);
        }
    }

    public boolean deleteCompanyID(String cid) {

        boolean bool = false;
        try {
            dbAccess.deleteCompanyData(cid);
            bool =  true;
            return bool;
        }
        catch (Throwable e) {
            return bool;
        }

    }

}