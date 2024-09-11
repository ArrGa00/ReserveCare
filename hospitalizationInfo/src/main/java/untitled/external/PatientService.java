package untitled.external;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.Date;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
@FeignClient(name = "patient", url = "${api.url.patient}")
public interface PatientService {
    /*
    @RequestMapping(method= RequestMethod., path="/patients/{id}")
    public void getNameById(@PathVariable("id") Long id , @RequestBody GetNameByIdCommand getNameByIdCommand );
    @GetMapping(path="/patients/search/findByGetNameById/{id}")
    public Patient getNameById(@PathVariableGetNameByIdQuery getNameByIdQuery);
    */

    //requestbody가 필요한가?
    @PostMapping(path = "/patients/{id}")
    public void getNameById(@PathVariable("id") Long id, @RequestBody GetNameByIdCommand getNameByIdCommand);

    @GetMapping(path = "/patients/search/findByGetNameById/{id}")
    public Patient getNameById(@PathVariable("id") Long id);
}