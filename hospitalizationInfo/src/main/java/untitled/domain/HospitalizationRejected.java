package untitled.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import untitled.domain.*;
import untitled.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class HospitalizationRejected extends AbstractEvent {

    private Long id;
    private Long patientId;
    private Long reservationId;

    public HospitalizationRejected(HospitalizationInfo aggregate) {
        super(aggregate);
    }

    public HospitalizationRejected() {
        super();
    }
}
//>>> DDD / Domain Event
