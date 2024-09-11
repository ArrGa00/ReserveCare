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
@RequestMapping(value="/hospitals")
@Transactional
public class HospitalController {

    @Autowired
    HospitalRepository hospitalRepository;

    @RequestMapping(
        value = "/initializebeds",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8"
    )
    public Hospital initializeBeds(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println("##### /initializeBeds  called #####");

        Hospital hospital = new Hospital();
        hospital.initializeBeds();

        return hospital;
    }
}
//>>> Clean Arch / Inbound Adaptor
