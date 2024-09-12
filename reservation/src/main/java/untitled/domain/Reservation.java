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


    @PostPersist
    public void onPostPersist() {
        this.setStatus("요청");
        HospitalizationReserved hospitalizationReserved = new HospitalizationReserved(this);
        hospitalizationReserved.publishAfterCommit();

        repository().findById(hospitalizationReserved.getId()).ifPresent(reservation->{
            reservation.setStatus("요청");
            repository().save(reservation);
         });
    }

    public static ReservationRepository repository() {
        ReservationRepository reservationRepository = ReservationApplication.applicationContext.getBean(
            ReservationRepository.class
        );
        return reservationRepository;
    }

    public void hospitalizationCancel() {
        HospitalizationCancelled hospitalizationCancelled = new HospitalizationCancelled(this);
        hospitalizationCancelled.publishAfterCommit();
    }

    public static void updateStatus(
        HospitalizationApproved hospitalizationApproved
    ) {
        repository().findById(hospitalizationApproved.getReservationId()).ifPresent(reservation->{
            reservation.setStatus("환자이송");
            repository().save(reservation);
         });

    }

    public static void updateStatus(
        HospitalizationRejected hospitalizationRejected
    ) {
        repository().findById(hospitalizationRejected.getReservationId()).ifPresent(reservation->{
            reservation.setStatus("거절"); 
            repository().save(reservation);
         });
    }

}
//>>> DDD / Aggregate Root
