package stalkervr.library.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import org.springframework.stereotype.Repository;
import stalkervr.library.entity.IssuanceLog;
import stalkervr.library.entity.Publication;
import stalkervr.library.entity.UserLibrary;

import java.util.List;

@Repository
public interface IssuanceLogRepository extends PagingAndSortingRepository<IssuanceLog, Long> {

    List<IssuanceLog> findIssuanceLogByUserLibraryAndPublicationAndOperation
            (UserLibrary userLibrary, Publication publication, String operation);

    List<IssuanceLog> findAllByUserLibraryId(Long id);
}
