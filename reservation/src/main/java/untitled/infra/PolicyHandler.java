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

@Service
@Transactional
public class PolicyHandler {

    @Autowired
    ReservationRepository reservationRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}


    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='HospitalizationApproved'"
    )
    public void wheneverHospitalizationApproved_UpdateStatus(
        @Payload HospitalizationApproved hospitalizationApproved
    ) {
        HospitalizationApproved event = hospitalizationApproved;
        System.out.println(
            "\n\n##### listener UpdateStatus : " +
            hospitalizationApproved +
            "\n\n"
        );

        Reservation.updateStatus(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='HospitalizationRejected'"
    )
    public void wheneverHospitalizationRejected_UpdateStatus(
        @Payload HospitalizationRejected hospitalizationRejected
    ) {
        HospitalizationRejected event = hospitalizationRejected;
        System.out.println(
            "\n\n##### listener UpdateStatus : " +
            hospitalizationRejected +
            "\n\n"
        );

        Reservation.updateStatus(event);
    }
}

