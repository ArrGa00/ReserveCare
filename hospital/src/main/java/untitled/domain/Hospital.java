package untitled.domain;

import untitled.HospitalApplication;
import javax.persistence.*;
import java.util.List;
import lombok.Data;
import java.util.Date;
import java.time.LocalDate;


@Entity
@Table(name="Hospital_table")
@Data

//<<< DDD / Aggregate Root
public class Hospital  {


    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    
    
    
    
    private Long id;
    
    
    
    
    private String hospitalName;
    
    
    
    
    private Double lat;
    
    
    
    
    private Double lng;
    
    
    
    
    private Integer remain;
    
    
    
    
    private Integer totalBeds;
    
    
    
    
    private String hpid;

    @PostPersist
    public void onPostPersist(){
    
    }

    public static HospitalRepository repository(){
        HospitalRepository hospitalRepository = HospitalApplication.applicationContext.getBean(HospitalRepository.class);
        return hospitalRepository;
    }

    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }
    public void initialize beds(){
        //
    }


//<<< Clean Arch / Port Method
    public void initializeBeds(){
        
        //implement business logic here:
        

    }
//>>> Clean Arch / Port Method

//<<< Clean Arch / Port Method
    public static void bedsUpdate(HospitalizationApproved hospitalizationApproved){
        
        //implement business logic here:

        /** Example 1:  new item 
        Hospital hospital = new Hospital();
        repository().save(hospital);

        */

        /** Example 2:  finding and process
        
        repository().findById(hospitalizationApproved.get???()).ifPresent(hospital->{
            
            hospital // do something
            repository().save(hospital);


         });
        */

        
    }
//>>> Clean Arch / Port Method
//<<< Clean Arch / Port Method
    public static void bedsUpdate(Discharged discharged){
        
        //implement business logic here:

        /** Example 1:  new item 
        Hospital hospital = new Hospital();
        repository().save(hospital);

        */

        /** Example 2:  finding and process
        
        repository().findById(discharged.get???()).ifPresent(hospital->{
            
            hospital // do something
            repository().save(hospital);


         });
        */

        
    }
//>>> Clean Arch / Port Method


}
//>>> DDD / Aggregate Root
