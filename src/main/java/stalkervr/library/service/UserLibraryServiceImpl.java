package stalkervr.library.service;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import stalkervr.library.entity.IssuanceLog;
import stalkervr.library.entity.Publication;
import stalkervr.library.entity.StatusLog;
import stalkervr.library.entity.UserLibrary;
import stalkervr.library.repository.IssuanceLogRepository;
import stalkervr.library.repository.PublicationRepository;
import stalkervr.library.repository.UserLibraryRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserLibraryServiceImpl implements UserLibraryService {

    private final UserLibraryRepository userLibraryRepository;
    private final PublicationRepository publicationRepository;
    private final IssuanceLogRepository issuanceLogRepository;

    @PersistenceContext
    private final EntityManager entityManager;

    @Autowired
    public UserLibraryServiceImpl(UserLibraryRepository userLibraryRepository, PublicationRepository publicationRepository,
                                  IssuanceLogRepository issuanceLogRepository, EntityManager entityManager) {
        this.userLibraryRepository = userLibraryRepository;
        this.publicationRepository = publicationRepository;
        this.issuanceLogRepository = issuanceLogRepository;
        this.entityManager = entityManager;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void addUser(UserLibrary userLibrary) {
        if (userLibrary != null) {
            userLibraryRepository.save(userLibrary);
            log.info("IN UserLibraryServiceImpl addUser() id - {}", userLibrary.getId());
        } else {
            log.warn("IN UserLibraryServiceImpl addUser() sent user == null !!!");
        }
    }

    @Override
    public Optional<UserLibrary> getUserById(Long id) {

        return userLibraryRepository.findById(id);
    }

    @Override
    public Page<UserLibrary> getAllUserPage(PageRequest pageRequest) {
        return userLibraryRepository.findAll(pageRequest);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void takeBook(UserLibrary userLibrary, Publication publication) {

        if(publication.getBookIssued() > 0){
            issuanceLogRepository.save(new IssuanceLog(StatusLog.EXTRADITION.toString(),userLibrary, publication));
            publication.setBookIssued(publication.getBookIssued() - 1);
            publicationRepository.save(publication);
        } else {
            log.info("IN UserLibraryServiceImpl takeBook() bookIssued = {}", publication.getBookIssued());
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,  isolation = Isolation.READ_COMMITTED)
    public void returnBook(UserLibrary userLibrary, Publication publication) {

        if(userLibrary != null && publication != null){

            Long userId = userLibrary.getId();
            Long publicationId = publication.getId();

            List list = entityManager
                    .createQuery("select t from IssuanceLog t where t.publication.id =: publicationId and t.userLibrary.id =: userId")
                    .setParameter("publicationId", publicationId)
                    .setParameter("userId", userId)
                    .getResultList();

            if(list.isEmpty()){
                log.info("IN UserLibraryServiceImpl returnBook() The user did not receive this book !!!");
            } else {
                IssuanceLog issuanceLog = new IssuanceLog(StatusLog.RETURN.toString(),userLibrary, publication);
                issuanceLog.setDateReturn(Instant.now());
                issuanceLogRepository.save(issuanceLog);
                publication.setBookIssued(publication.getBookIssued() + 1);
                publicationRepository.save(publication);
            }
        }
    }
}
