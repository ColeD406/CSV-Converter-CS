package clockshark.csvconverter.main;

import clockshark.csvconverter.main.database.DBAccess;
import clockshark.csvconverter.main.model.Company;
import clockshark.csvconverter.main.model.JSONResponse;
import clockshark.csvconverter.main.service.ClockSharkCSVService;
import clockshark.csvconverter.main.service.TemplateBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@SpringBootApplication
@RestController
public class MainApplication {
	private static final Gson gson = new Gson();
	private final TemplateBuilder templateBuilder;
	private final ClockSharkCSVService clockSharkCSVService;
	private final DBAccess dbAccess;
	ObjectMapper mapper = new ObjectMapper();

	@Autowired
	public MainApplication(TemplateBuilder templateBuilder, ClockSharkCSVService clockSharkCSVService, DBAccess dbAccess) {
		this.templateBuilder = templateBuilder;
		this.clockSharkCSVService = clockSharkCSVService;
		this.dbAccess = dbAccess;
	}

	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}

	@PostMapping("/csv-new")
	@CrossOrigin("http://localhost:3000")
	public ResponseEntity<?> handleFormFields(@RequestParam("formValues") String formValues, @RequestPart("csvFile") MultipartFile csvFile, @RequestParam("companyID") String cid, @RequestParam("email") String email, @RequestParam("payComponent") String payComponent) throws IOException {
		try {
			dbAccess.saveCompanyData(cid, formValues, email, payComponent);
			templateBuilder.createCSV(clockSharkCSVService.newCsvData(csvFile, formValues, cid, email, payComponent), email);
		}
		catch (IOException e) {
			JSONResponse response = new JSONResponse("Error");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		JSONResponse response = new JSONResponse("Request Received");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/csv-saved")
	@CrossOrigin("http://localhost:3000")
	public  ResponseEntity<?> savedCSVTemplate(@RequestParam("companyID")String cid, @RequestPart("csvFile") MultipartFile csvFile) {
		Company company = dbAccess.savedCompanyTemplate(cid);
		String email = company.getEmail() ;
		try {
			templateBuilder.createCSV(clockSharkCSVService.savedCSVData(csvFile, cid), email);
		}
		catch (IOException e) {
			JSONResponse response = new JSONResponse("Error");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		JSONResponse response = new JSONResponse("Request Received");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}

