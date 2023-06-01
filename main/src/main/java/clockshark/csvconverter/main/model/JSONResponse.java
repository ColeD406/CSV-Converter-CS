package clockshark.csvconverter.main.model;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class JSONResponse {
    private String response;

    public JSONResponse(String requestReceived) {
    }
}
