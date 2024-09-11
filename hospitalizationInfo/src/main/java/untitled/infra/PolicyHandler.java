package untitled.infra;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import main.java.untitled.external.GetNameById;

import java.util.Date;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.naming.NameParser;
import javax.naming.NameParser;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import untitled.config.kafka.KafkaProcessor;
import untitled.domain.*;
import untitled.external.PatientService;

@Service
@Transactional
public class PolicyHandler {

    @Autowired
    HospitalizationInfoRepository hospitalizationInfoRepository;

    @Autowired
    PatientService patientService;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='HospitalizationReserved'"
    )
    public void wheneverHospitalizationReserved_CreateHospitalInfo(
        @Payload HospitalizationReserved hospitalizationReserved
    ) {
        HospitalizationReserved event = hospitalizationReserved;
        System.out.println(
            "\n\n##### listener CreateHospitalInfo : " +
            hospitalizationReserved +
            "\n\n"
        );

        HospitalizationInfo.createHospitalInfo(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='HospitalizationCancelled'"
    )
    public void wheneverHospitalizationCanceled_UpdateStatus(
        @Payload HospitalizationCancelled hospitalizationCancelled
    ) {
        HospitalizationCancelled event = hospitalizationCancelled;
        System.out.println(
            "\n\n##### listener UpdateStatus : " +
            hospitalizationCancelled +
            "\n\n"
        );

        HospitalizationInfo.updateStatus(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='HospitalizationApproved'"
    )
    public void wheneverHospitalizationApproved_SearchPatientInfo (
        @Payload HospitalizationApproved hospitalizationApproved
    ) {
        try {
            HospitalizationApproved event = hospitalizationApproved;
            System.out.println(
                "\n\n##### listener Test : " + hospitalizationApproved + "\n\n"
            );
    
            Optional<HospitalizationInfo> optionalHospitalizationInfo = hospitalizationInfoRepository.findById(
                hospitalizationApproved.getId()
            );
            HospitalizationInfo hospitalizationInfo = optionalHospitalizationInfo
                .orElseThrow(() -> new Exception("No Entity Found"));
    
            // REST Request
            System.out.println("req 요청을 보내도록 하겠습니다.");
            GetNameById patientInfo = patientService.getPatientNameById(hospitalizationApproved.getPatientId());
            System.out.println("\n\n\n\n\n" + patientInfo.getPatientName() );
    
            hospitalizationInfo.setPatientName(patientInfo.getPatientName());
            hospitalizationInfo.setStatus("승인");
            hospitalizationInfo.setStartDate(LocalDateTime.now());
            hospitalizationInfoRepository.save(hospitalizationInfo);
            
        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
    }
}