package stalkervr.library.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import stalkervr.library.entity.IssuanceLog;

import java.util.List;

public interface IssuanceLogService {

    Page<IssuanceLog> getAllIssuance(PageRequest pageRequest);

    List<IssuanceLog> getAllIssuanceByUserId(Long userId);
}
