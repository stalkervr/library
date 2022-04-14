package stalkervr.library.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import org.springframework.stereotype.Repository;
import stalkervr.library.entity.IssuanceLog;

@Repository
public interface IssuanceLogRepository extends PagingAndSortingRepository<IssuanceLog, Long> {
}
