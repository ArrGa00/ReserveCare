package untitled.domain;

import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import untitled.domain.*;

//<<< PoEAA / Repository
@RepositoryRestResource(collectionResourceRel = "patients", path = "patients")
public interface PatientRepository
    extends PagingAndSortingRepository<Patient, Long> {
    @Query(
        value = "select patient " +
        "from Patient patient " +
        "where(:id is null or patient.id = :id)"
    )
    Patient findByGetNameById(Long id);
}
