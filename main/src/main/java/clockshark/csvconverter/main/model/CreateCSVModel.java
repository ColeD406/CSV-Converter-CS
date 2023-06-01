package clockshark.csvconverter.main.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "CreateCSV")
public class CreateCSVModel {
    private Map<String,String> employeeData;
    private MultipartFile csvData;

    public Map<String, String> getEmployeeData() {
        return employeeData;
    }

    public void setEmployeeData(Map<String,String> employeeData) {
        this.employeeData = employeeData;
    }

    public MultipartFile getCsvData() {
        return csvData;
    }

    public void setCsvData(MultipartFile csvData) {
        this.csvData = csvData;
    }
}
