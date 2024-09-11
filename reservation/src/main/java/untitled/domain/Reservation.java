package untitled.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import untitled.ReservationApplication;
import untitled.domain.HospitalizationReserved;

@Entity
@Table(name = "Reservation_table")
@Data
//<<< DDD / Aggregate Root
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long patientId;

    private Long bedsId;

    private String status;

    @PrePersist
    public void onPrePersist() {
        this.setStatus("병원승인 대기");
        HospitalizationReserved hospitalizationReserved = new HospitalizationReserved(this);
        hospitalizationReserved.publishAfterCommit();
    }

    public static ReservationRepository repository() {
        ReservationRepository reservationRepository = ReservationApplication.applicationContext.getBean(
            ReservationRepository.class
        );
        return reservationRepository;
    }

    public void hospitalizationCancel() {
        //implement business logic here:

        HospitalizationCanceled hospitalizationCanceled = new HospitalizationCanceled(
            this
        );
        hospitalizationCanceled.publishAfterCommit();
    }

    public static void updateStatus(
        HospitalizationApproved hospitalizationApproved
    ) {
        
        repository().findById(hospitalizationApproved.getHospitalizationId()).ifPresent(hospitalization->{
            hospitalization.setStatus("환자이송");
            repository().save(hospitalization);
         });

    }

    public static void updateStatus(
        HospitalizationRejected hospitalizationRejected
    ) {
        repository().findById(hospitalizationRejected.getHospitalizationId()).ifPresent(hospitalization->{
            hospitalization.setStatus("요청거절"); // do something
            repository().save(hospitalization);
         });

    }

}
//>>> DDD / Aggregate Root
