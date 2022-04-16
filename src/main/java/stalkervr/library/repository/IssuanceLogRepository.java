package stalkervr.library.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import org.springframework.stereotype.Repository;
import stalkervr.library.entity.IssuanceLog;
import stalkervr.library.entity.UserLibrary;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface IssuanceLogRepository extends PagingAndSortingRepository<IssuanceLog, Long> {

}
