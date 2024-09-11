package untitled.infra;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import untitled.domain.*;

//<<< Clean Arch / Inbound Adaptor

@RestController
// @RequestMapping(value="/reservations")
@Transactional
public class ReservationController {

    @Autowired
    ReservationRepository reservationRepository;

    @RequestMapping(
        value = "/reservations/{id}/hospitalizationcancel",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8"
    )
    public Reservation hospitalizationCancel(
        @PathVariable(value = "id") Long id,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println(
            "##### /reservation/hospitalizationCancel  called #####"
        );
        Optional<Reservation> optionalReservation = reservationRepository.findById(
            id
        );

        optionalReservation.orElseThrow(() -> new Exception("No Entity Found"));
        Reservation reservation = optionalReservation.get();
        reservation.hospitalizationCancel();
        
        reservation.setStatus("요청취소");
        reservationRepository.save(reservation);
        return reservation;
    }
}
//>>> Clean Arch / Inbound Adaptor
