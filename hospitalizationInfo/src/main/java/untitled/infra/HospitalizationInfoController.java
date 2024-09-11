package untitled.infra;

import java.time.LocalDateTime;
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
// @RequestMapping(value="/hospitalizationInfos")
@Transactional
public class HospitalizationInfoController {

    @Autowired
    HospitalizationInfoRepository hospitalizationInfoRepository;

    @RequestMapping(
        value = "/hospitalizationInfos/{id}/approve",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8"
    )
    public HospitalizationInfo approve(
        @PathVariable(value = "id") Long id,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println("##### /hospitalizationInfo/approve  called #####");
        Optional<HospitalizationInfo> optionalHospitalizationInfo = hospitalizationInfoRepository.findById(
            id
        );

        optionalHospitalizationInfo.orElseThrow(() ->
            new Exception("No Entity Found")
        );
        HospitalizationInfo hospitalizationInfo = optionalHospitalizationInfo.get();
        hospitalizationInfo.approve();
        // policy에서 startdate를 설정함
        hospitalizationInfo.setStartDate(LocalDateTime.now());
        hospitalizationInfo.setStatus("승인");
        hospitalizationInfoRepository.save(hospitalizationInfo);
        return hospitalizationInfo;
    }

    @RequestMapping(
        value = "/hospitalizationInfos/{id}/discharge",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8"
    )
    public HospitalizationInfo discharge(
        @PathVariable(value = "id") Long id,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println(
            "##### /hospitalizationInfo/discharge  called #####"
        );
        Optional<HospitalizationInfo> optionalHospitalizationInfo = hospitalizationInfoRepository.findById(
            id
        );

        optionalHospitalizationInfo.orElseThrow(() ->
            new Exception("No Entity Found")
        );
        HospitalizationInfo hospitalizationInfo = optionalHospitalizationInfo.get();
        hospitalizationInfo.discharge();

        hospitalizationInfo.setStatus("퇴원");
        hospitalizationInfo.setEndDate(LocalDateTime.now());
        hospitalizationInfoRepository.save(hospitalizationInfo);
        return hospitalizationInfo;
    }

    @RequestMapping(
        value = "/hospitalizationInfos/{id}/reject",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8"
    )
    public HospitalizationInfo reject(
        @PathVariable(value = "id") Long id,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println("##### /hospitalizationInfo/reject  called #####");
        Optional<HospitalizationInfo> optionalHospitalizationInfo = hospitalizationInfoRepository.findById(id);

        optionalHospitalizationInfo.orElseThrow(() ->
            new Exception("No Entity Found")
        );
        HospitalizationInfo hospitalizationInfo = optionalHospitalizationInfo.get();
        hospitalizationInfo.reject();

        hospitalizationInfo.setStatus("거절");
        hospitalizationInfoRepository.save(hospitalizationInfo);
        return hospitalizationInfo;
    }
}
//>>> Clean Arch / Inbound Adaptor
