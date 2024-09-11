package untitled.domain;

import java.time.LocalDate;
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

    private Date startDate;

    private Date endDate;

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
        Hospital hospital = new Hospital();
        hospital.setBedsId(hospitalizationReserved.getBedsId());
        hospital.setPatientId(hospitalizationReserved.getPatientId());
        hospital.setHospitalizationId(hospitalizationReserved.getId());
        hospital.setStatus("요청 받음");
        repository().save(hospital);
    }

    public static void updateStatus(
        HospitalizationCanceled hospitalizationCanceled
    ) {
        repository().findById(Long.valueOf(hospitalizationCancelled.getBedsId())).ifPresent(hospital->{
            
            if (!hospital.getStatus().equals("승인")){
                hospital.setStatus("예약취소됨");
                repository().save(hospital);
            } else {
                // 이벤트를 발행해야하는지 -> hospitalization과 hospital 상태 2개를 취소로 변경만 함
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
