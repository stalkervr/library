package stalkervr.library.service;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import stalkervr.library.entity.IssuanceLog;
import stalkervr.library.exception.BadRequestException;
import stalkervr.library.exception.NotFoundException;
import stalkervr.library.repository.IssuanceLogRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class IssuanceLogServiceImpl implements IssuanceLogService{

    private final IssuanceLogRepository issuanceLogRepository;

    @Autowired
    public IssuanceLogServiceImpl(IssuanceLogRepository issuanceLogRepository) {
        this.issuanceLogRepository = issuanceLogRepository;
    }

    @Override
    public Page<IssuanceLog> getAllIssuance(PageRequest pageRequest)
            throws NotFoundException {

        Page<IssuanceLog> issuanceLogPage = issuanceLogRepository.findAll(pageRequest);

        if(issuanceLogPage.isEmpty()){
            log.warn("IN method getAllIssuance(PageRequest pageRequest) list issuanceLog is empty !!!");
            throw new NotFoundException("IN method getAllIssuance(PageRequest pageRequest) list issuanceLog is empty !!!");
        } else {
            log.info("IN method getAllIssuance(PageRequest pageRequest) list issuanceLog is OK !!!");
            return issuanceLogPage;
        }
    }

    @Override
    public Page <IssuanceLog> getAllIssuanceByUserId(Integer page, Long userId)
            throws NotFoundException, BadRequestException {

        if(userId == null){
            log.warn("IN method getAllIssuanceByUserId() in sent params userId = null !!!");
            throw new BadRequestException("IN method getAllIssuanceByUserId() in sent params userId = null !!!");
        }

        List<IssuanceLog>issuanceLogList = issuanceLogRepository.findAllByUserLibraryId(userId);

        if(issuanceLogList.isEmpty()){
            log.warn("No records for userLibrary with userId = {} !!!", userId);
            throw new NotFoundException ("No records for userLibrary with userId = " + userId);
        } else {
            return new PageImpl<>(issuanceLogList, PageRequest.of((Optional.of(page)).orElse(0),5), 0);
        }
    }
}
