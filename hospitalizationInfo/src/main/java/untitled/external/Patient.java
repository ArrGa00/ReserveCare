package untitled.external;

import java.util.Date;
import lombok.Data;

@Data
public class Patient {

    private Long id;
    private String patientName;
    private String patientDisease;
    private String patientNumber;
    private String status;
}
