package clockshark.csvconverter.main.service;
import clockshark.csvconverter.main.database.DBAccess;
import clockshark.csvconverter.main.model.Company;
import clockshark.csvconverter.main.object.ClockSharkCSV;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.input.BOMInputStream;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class ClockSharkCSVService {

    private final DBAccess dbAccess;

    public ClockSharkCSVService(DBAccess dbAccess) {
        this.dbAccess = dbAccess;
    }
    public List<List<String>> newCsvData(MultipartFile csvData, String formValuesAsString, String cid, String email, String payComponent) throws IOException {
        Map<String, String> formValues = jsonToMap(formValuesAsString);

        Reader reader = new BufferedReader(new InputStreamReader(new BOMInputStream(csvData.getInputStream()), StandardCharsets.UTF_8));
        CSVParser parser = CSVFormat.DEFAULT.withHeader().withSkipHeaderRecord().parse(reader);
        Map<String, Integer> headerMap = parser.getHeaderMap();

        List<String> keys = new ArrayList(formValues.keySet());
        List<String> values = new ArrayList(formValues.values());
        List<List<String>> finalList = new ArrayList<>();

        if (payComponent.equals("true")) {
            List<String> listWithPayComponent = new ArrayList<String>(values);
            listWithPayComponent.add("Pay Component");
            listWithPayComponent.add("Time");
            finalList.add(listWithPayComponent);
        }
        else {
            finalList.add(values);
        }
        finalList.addAll(createCSV(csvInfo(parser), keys.toArray(new String[0]), payComponent));

        return finalList;
    }

    public List<List<String>> savedCSVData(MultipartFile csvData, String cid) throws IOException {
        Company company = dbAccess.savedCompanyTemplate(cid);

        return newCsvData(csvData, company.getAttributes(), cid, company.getEmail(), company.getPayComponent());
    }

    public Map<String, String> jsonToMap(String jsonString) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> resultMap = null;
        try {
            resultMap = objectMapper.readValue(jsonString, Map.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return resultMap;
    }

    public List<ClockSharkCSV> csvInfo(CSVParser parser) {
        Map<String, Integer> headerMap = parser.getHeaderMap();
        List<ClockSharkCSV> list = new ArrayList<>();
        for (CSVRecord record : parser) {
            ClockSharkCSV clockSharkCSV = new ClockSharkCSV();
            clockSharkCSV.setFirstName(record.get(0));
            clockSharkCSV.setLastName(record.get(1));
            clockSharkCSV.setEmployeeId(record.get(2));
            clockSharkCSV.setEmail(record.get(3));
            if (record.get(4).contains(":")) {
                clockSharkCSV.setCustomer(jobCustomer(record.get(4))[0]);
                clockSharkCSV.setJob(jobCustomer(record.get(4))[1]);
            }else {
                clockSharkCSV.setJob(record.get(4));
            }
            clockSharkCSV.setJobNumber(record.get(5));
            clockSharkCSV.setTask(record.get(6));
            clockSharkCSV.setTaskCode(record.get(7));
            clockSharkCSV.setStartDate(dateSplit(record.get(8))[0]);
            clockSharkCSV.setStartTime(dateSplit(record.get(8))[1]);
            clockSharkCSV.setEndDate(dateSplit(record.get(9))[0]);
            clockSharkCSV.setEndTime(dateSplit(record.get(9))[1]);
            clockSharkCSV.setMinutesBreak(record.get(10));
            clockSharkCSV.setMinutesBillable(record.get(11));
            clockSharkCSV.setMinutesTotal(record.get(12));
            clockSharkCSV.setTotalTime(record.get(17));
            clockSharkCSV.setRegularTime(record.get(18));
            clockSharkCSV.setOverTime(record.get(19));
            clockSharkCSV.setDoubleTime(record.get(20));
            clockSharkCSV.setTimeOff(record.get(21));
            clockSharkCSV.setUnpaidTimeOff(record.get(22));
            list.add(clockSharkCSV);
        }
        return list;
    }
    public List<List<String>> createCSV(List<ClockSharkCSV> list, String[] newKeys, String payComponent) {
        int count = 0;
        List<List<String>> finalList = new ArrayList<>();
        for (ClockSharkCSV entry : list) {
            List<String> row = new ArrayList<>();
            if (payComponent.equals("true")) {
                Map<String, String> payComponentMap = payComponentMap(list, count);
                for (Map.Entry<String, String> entry1 : payComponentMap.entrySet()) {
                    for (String newKey : newKeys) {
                        row.add(assignRowValues(newKey, list, count));
                    }
                    row.add(entry1.getKey());
                    row.add(entry1.getValue());
                    finalList.add(row);
                    row = new ArrayList<>();
                }
                count++;
            } else {
                for (String newKey : newKeys) {
                    row.add(assignRowValues(newKey, list, count));
                }
                finalList.add(row);
                count++;
            }
        }
        return finalList;

    }
    public String[] jobCustomer (String entry) {
        return entry.split(":");
    }

    public String[] dateSplit(String entry) {
        return entry.split(" ");
    }

    public String assignRowValues(String s, List<ClockSharkCSV> list, int count) {
        return switch (s) {
            case "EmployeeID" -> list.get(count).getEmployeeId();
            case "EmployeeFName" -> list.get(count).getFirstName();
            case "EmployeeLName" -> list.get(count).getLastName();
            case "EmployeeEmail" -> list.get(count).getEmail();
            case "Customer" -> list.get(count).getCustomer();
            case "Job" -> list.get(count).getJob();
            case "JobNumber" -> list.get(count).getJobNumber();
            case "Task" -> list.get(count).getTask();
            case "TaskCode" -> list.get(count).getTaskCode();
            case "StartDate" -> list.get(count).getStartDate();
            case "StartTime" -> list.get(count).getStartTime();
            case "EndDate" -> list.get(count).getEndDate();
            case "EndTime" -> list.get(count).getEndTime();
            case "MinutesBillable" -> list.get(count).getMinutesBillable();
            case "TotalTime" -> list.get(count).getTotalTime();
            case "Regular" -> list.get(count).getRegularTime();
            case "OverTime" -> list.get(count).getOverTime();
            case "DoubleTime" -> list.get(count).getDoubleTime();
            default -> "";
        };
    }
    Map<String, String> payComponentMap (List<ClockSharkCSV> list, int count){
        Map<String, String> payComponent = new HashMap<>();
        if (!list.get(count).getRegularTime().equals("0")) {
            payComponent.put("Reg", list.get(count).getRegularTime());
        }
        if (!list.get(count).getOverTime().equals("0")) {
            payComponent.put("OT", list.get(count).getOverTime());
        }
        if (!list.get(count).getDoubleTime().equals("0")) {
            payComponent.put("DT", list.get(count).getDoubleTime());
        }
        if (!list.get(count).getTimeOff().equals("0")) {
            payComponent.put("PTO", list.get(count).getTimeOff());
        }
        if (!list.get(count).getUnpaidTimeOff().equals("0")) {
            payComponent.put("UPTO", list.get(count).getUnpaidTimeOff());
        }
        return payComponent;
    }
}

