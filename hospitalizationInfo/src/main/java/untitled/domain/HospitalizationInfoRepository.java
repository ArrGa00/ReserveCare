package untitled.domain;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import untitled.domain.*;

//<<< PoEAA / Repository
@RepositoryRestResource(
    collectionResourceRel = "hospitalizationInfos",
    path = "hospitalizationInfos"
)
public interface HospitalizationInfoRepository
    extends PagingAndSortingRepository<HospitalizationInfo, Long> {
    @Query(
        value = "select hospitalizationInfo " +
        "from HospitalizationInfo hospitalizationInfo " +
        "where(:bedsId is null or hospitalizationInfo.bedsId = :bedsId)"
    )
    List<HospitalizationInfo> findByReservationInfo(
        Long bedsId,
        Pageable pageable
    );

    Optional<HospitalizationInfo> findByReservationId(Long reservationId);
}
