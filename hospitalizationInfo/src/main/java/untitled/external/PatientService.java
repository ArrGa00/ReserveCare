package untitled.external;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import main.java.untitled.external.GetNameById;

import java.util.Date;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
@FeignClient(name = "patient", url = "${api.url.patient}")
public interface PatientService {
    
    @RequestMapping(method= RequestMethod.GET, path="patients/getNamebyId/{id}")
    public GetNameById getPatientNameById(@PathVariable("id") Long id);
 
}