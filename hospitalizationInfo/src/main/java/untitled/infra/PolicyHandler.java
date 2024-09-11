package untitled.infra;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.naming.NameParser;
import javax.naming.NameParser;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import untitled.config.kafka.KafkaProcessor;
import untitled.domain.*;

//<<< Clean Arch / Inbound Adaptor
@Service
@Transactional
public class PolicyHandler {

    @Autowired
    HospitalizationInfoRepository hospitalizationInfoRepository;

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
        condition = "headers['type']=='HospitalizationCanceled'"
    )
    public void wheneverHospitalizationCanceled_UpdateStatus(
        @Payload HospitalizationCanceled hospitalizationCanceled
    ) {
        HospitalizationCanceled event = hospitalizationCanceled;
        System.out.println(
            "\n\n##### listener UpdateStatus : " +
            hospitalizationCanceled +
            "\n\n"
        );

        // Sample Logic //
        HospitalizationInfo.updateStatus(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='HospitalizationApproved'"
    )
    public void wheneverHospitalizationApproved_Test(
        @Payload HospitalizationApproved hospitalizationApproved
    ) {
        HospitalizationApproved event = hospitalizationApproved;
        System.out.println(
            "\n\n##### listener Test : " + hospitalizationApproved + "\n\n"
        );
        // REST Request Sample

        // getNameByIdService.getGetNameById(/** mapping value needed */);

        // Sample Logic //

    }
}
//>>> Clean Arch / Inbound Adaptor
