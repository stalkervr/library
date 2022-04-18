package stalkervr.library.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import stalkervr.library.entity.IssuanceLog;
import stalkervr.library.exception.BadRequestException;
import stalkervr.library.exception.NotFoundException;

public interface IssuanceLogService {

    Page<IssuanceLog> getAllIssuance(PageRequest pageRequest) throws NotFoundException;

    Page<IssuanceLog> getAllIssuanceByUserId(Integer page, Long userId) throws NotFoundException, BadRequestException;
}
