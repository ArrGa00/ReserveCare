package untitled.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import untitled.HospitalizationinfoApplication;
import untitled.domain.HospitalizationApproved;

@Entity
@Table(name = "HospitalizationInfo_table")
@Data
//<<< DDD / Aggregate Root
public class HospitalizationInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long bedsId;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Long patientId;

    private String status;

    private Long reservationId;

    private String patientName;

    @PostPersist
    public void onPostPersist() {

    }

    public static HospitalizationInfoRepository repository() {
        HospitalizationInfoRepository hospitalizationInfoRepository = HospitalizationinfoApplication.applicationContext.getBean(
            HospitalizationInfoRepository.class
        );
        return hospitalizationInfoRepository;
    }

    public void approve() {
        HospitalizationApproved hospitalizationApproved = new HospitalizationApproved(this);
        hospitalizationApproved.publishAfterCommit();
    }

    public void reject() {
        HospitalizationRejected hospitalizationRejected = new HospitalizationRejected(this);
        hospitalizationRejected.publishAfterCommit();
    }

    public void discharge() {
        Discharged discharged = new Discharged(this);
        discharged.publishAfterCommit();
    }

    public static void createHospitalInfo(
        HospitalizationReserved hospitalizationReserved
    ) {
        HospitalizationInfo hospitalizationInfo = new HospitalizationInfo();
        hospitalizationInfo.setBedsId(hospitalizationReserved.getBedsId());
        hospitalizationInfo.setPatientId(hospitalizationReserved.getPatientId());
        hospitalizationInfo.setReservationId(hospitalizationReserved.getId());
        hospitalizationInfo.setStatus("요청");
        repository().save(hospitalizationInfo);
    }

    public static void updateStatus(
        HospitalizationCancelled hospitalizationCancelled
    ) {
        repository().findByReservationId(hospitalizationCancelled.getId()).ifPresent(hospitalInfo->{
            // hospitalization과 hospital 상태 2개를 취소로 변경만 함
            if (!hospitalInfo.getStatus().equals("승인")){
                hospitalInfo.setStatus("요청취소");
                repository().save(hospitalInfo);
            } else {
                // 이벤트를 발행해야하는지 -> 발행 안하고 취소가 안됨을 출력만 함.
                System.out.println(
                    "\n\n##### 예약취소 불가능함 : " +
                    "hospital.java - updateStatus 에서 예외처리" +
                    "\n\n"
                );
            }
         });

    }
    

}
//>>> DDD / Aggregate Root
