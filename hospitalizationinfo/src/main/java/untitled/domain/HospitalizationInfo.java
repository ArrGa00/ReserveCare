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

    private Long hospitalizationId;

    private String patientName;

    @PostPersist
    public void onPostPersist() {
        HospitalizationApproved hospitalizationApproved = new HospitalizationApproved(
            this
        );
        hospitalizationApproved.publishAfterCommit();
    }

    public static HospitalizationInfoRepository repository() {
        HospitalizationInfoRepository hospitalizationInfoRepository = HospitalizationinfoApplication.applicationContext.getBean(
            HospitalizationInfoRepository.class
        );
        return hospitalizationInfoRepository;
    }

    //<<< Clean Arch / Port Method
    public void approve() {
        //implement business logic here:

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void discharge() {
        //implement business logic here:

        Discharged discharged = new Discharged(this);
        discharged.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void reject() {
        //implement business logic here:

        HospitalizationRejected hospitalizationRejected = new HospitalizationRejected(
            this
        );
        hospitalizationRejected.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method

    //<<< Clean Arch / Port Method
    public static void createHospitalInfo(
        HospitalizationReserved hospitalizationReserved
    ) {
        //implement business logic here:

        /** Example 1:  new item 
        HospitalizationInfo hospitalizationInfo = new HospitalizationInfo();
        repository().save(hospitalizationInfo);

        */

        /** Example 2:  finding and process
        
        repository().findById(hospitalizationReserved.get???()).ifPresent(hospitalizationInfo->{
            
            hospitalizationInfo // do something
            repository().save(hospitalizationInfo);


         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void updateStatus(
        HospitalizationCanceled hospitalizationCanceled
    ) {
        //implement business logic here:

        /** Example 1:  new item 
        HospitalizationInfo hospitalizationInfo = new HospitalizationInfo();
        repository().save(hospitalizationInfo);

        */

        /** Example 2:  finding and process
        
        repository().findById(hospitalizationCanceled.get???()).ifPresent(hospitalizationInfo->{
            
            hospitalizationInfo // do something
            repository().save(hospitalizationInfo);


         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
