package clockshark.csvconverter.main.object;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClockSharkCSV {
    private String firstName;
    private String lastName;
    private String employeeId;
    private String email;
    private String customer;
    private String job;
    private String jobNumber;
    private String task;
    private String taskCode;
    private String startDate;
    private String startTime;
    private String endDate;
    private String endTime;
    private String minutesBreak;
    private String minutesBillable;
    private String minutesTotal;
    private String totalTime;
    private String regularTime;
    private String overTime;
    private String doubleTime;
    private String timeOff;
    private String unpaidTimeOff;
}
