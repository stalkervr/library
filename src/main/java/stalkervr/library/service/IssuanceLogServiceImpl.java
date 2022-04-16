package stalkervr.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import stalkervr.library.entity.IssuanceLog;
import stalkervr.library.repository.IssuanceLogRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;

@Service
public class IssuanceLogServiceImpl implements IssuanceLogService{

    private final IssuanceLogRepository issuanceLogRepository;

    @PersistenceContext
    private final EntityManager entityManager;

    @Autowired
    public IssuanceLogServiceImpl(IssuanceLogRepository issuanceLogRepository, EntityManager entityManager) {
        this.issuanceLogRepository = issuanceLogRepository;
        this.entityManager = entityManager;
    }

    @Override
    public Page<IssuanceLog> getAllIssuance(PageRequest pageRequest) {
        return issuanceLogRepository.findAll(pageRequest);
    }

    @Override
    public List<IssuanceLog> getAllIssuanceByUserId(Long userId) {

        return entityManager
                .createQuery("select t from IssuanceLog t where t.userLibrary.id =: userId", IssuanceLog.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public Page <IssuanceLog> getAllIssuanceByUserId(Integer page, Long userId) {

        return new PageImpl<>(getAllIssuanceByUserId(userId),
                PageRequest.of(
                        (Optional.of(page)).orElse(0),5
                ), (Optional.of(userId)).orElse(1L)
        );
    }
}
