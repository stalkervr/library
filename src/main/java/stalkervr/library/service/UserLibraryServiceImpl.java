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
import stalkervr.library.exception.BadRequestException;
import stalkervr.library.exception.NotFoundException;
import stalkervr.library.repository.IssuanceLogRepository;
import stalkervr.library.repository.PublicationRepository;
import stalkervr.library.repository.UserLibraryRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserLibraryServiceImpl implements UserLibraryService {

    private final UserLibraryRepository userLibraryRepository;
    private final PublicationRepository publicationRepository;
    private final IssuanceLogRepository issuanceLogRepository;

    @Autowired
    public UserLibraryServiceImpl(UserLibraryRepository userLibraryRepository,
                                  PublicationRepository publicationRepository,
                                  IssuanceLogRepository issuanceLogRepository) {
        this.userLibraryRepository = userLibraryRepository;
        this.publicationRepository = publicationRepository;
        this.issuanceLogRepository = issuanceLogRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void addUser(UserLibrary userLibrary)
            throws BadRequestException {

        if (userLibrary == null || userLibrary.getId() == null){
            log.warn("IN UserLibraryServiceImpl addUser() sent user == null !!!");
            throw new BadRequestException("IN UserLibraryServiceImpl addUser() sent user == null !!!");

        } else if ((userLibraryRepository.findById(userLibrary.getId())).isEmpty()) {
            userLibraryRepository.save(userLibrary);
            log.info("In method addNewUser was created UserLibrary with id={}", userLibrary.getId());

        } else {
            log.warn("IN UserLibraryServiceImpl addUser(). Sent user always exist !!!");
            throw new BadRequestException("IN UserLibraryServiceImpl addUser(). Sent user always exist !!!");
        }
    }

    @Override
    public UserLibrary getUserById(Long id)
            throws NotFoundException, BadRequestException {

        if(id == null) {
            log.warn("IN method getUserById(Long id) error in sent parameters !!!");
            throw new BadRequestException("IN method getUserById(Long id) error in sent parameters !!!");
        }

        Optional<UserLibrary> userLibrary = userLibraryRepository.findById(id);

        if(userLibrary.isEmpty()) {
            log.warn("IN method getUserById(Long id) not found user with id={}", id);
            throw new NotFoundException("IN method getUserById(Long id) not found user with id=" + id);
        } else {
            log.info("IN method getUserById(Long id), found user with id={}", id);
            return userLibrary.get();
        }
    }

    @Override
    public Page<UserLibrary> getAllUserPage(PageRequest pageRequest)
            throws NotFoundException {

        Page<UserLibrary> allUserPage = userLibraryRepository.findAll(pageRequest);

        if(allUserPage.isEmpty()){
            log.warn("IN method getAllUserPage(PageRequest pageRequest) user's list is empty !!!");
            throw new NotFoundException("IN method getAllUserPage(PageRequest pageRequest) user's list is empty !!!");
        } else {
            log.info("IN IN method getAllUserPage(PageRequest pageRequest) user's list OK !!!");
            return allUserPage;
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void takeBook(UserLibrary userLibrary, Publication publication)
            throws NotFoundException {

        if(publication.getBookIssued() > 0){
            issuanceLogRepository.save(new IssuanceLog(StatusLog.EXTRADITION.toString(),userLibrary, publication));
            publication.setBookIssued(publication.getBookIssued() - 1);
            publicationRepository.save(publication);
            log.info("IN method takeBook() bookIssued = {} user with id={} take book --> {}",
                    publication.getBookIssued(), userLibrary.getId(), publication.getName());
        } else {
            log.warn("IN method takeBook() bookIssued = {} user with id={} can't take this book --> {}",
                    publication.getBookIssued(), userLibrary.getId(), publication.getName());
            throw new NotFoundException("IN method takeBook() bookIssued = "
                    + publication.getBookIssued() + " user with id = " + userLibrary.getId() +
                    " can't take this book --> " + publication.getName());
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,  isolation = Isolation.READ_COMMITTED)
    public void returnBook(UserLibrary userLibrary, Publication publication)
            throws NotFoundException {

        //if (userLibrary != null && publication != null){

            Long userId = userLibrary.getId();
            Long publicationId = publication.getId();

            List<IssuanceLog> listExtradition =
                    issuanceLogRepository.findIssuanceLogByUserLibraryAndPublicationAndOperation(
                            userLibrary, publication, StatusLog.EXTRADITION.toString());

            List<IssuanceLog> listReturn =
                    issuanceLogRepository.findIssuanceLogByUserLibraryAndPublicationAndOperation(
                            userLibrary, publication, StatusLog.RETURN.toString());

            if (listExtradition.isEmpty()){
                log.error("IN returnBook() The user with id={} did not receive this book with id_publication={} !!!",
                        userId, publicationId);
                throw new NotFoundException("IN returnBook() The user with id = " + userId +
                        " did not receive this book with id_publication = " + publicationId + " !!!");

            } else if(listExtradition.size() == listReturn.size()) {
                log.error("IN returnBook() The user with id={} always return this book with id_publication={} !!!",
                        userId, publicationId);
                throw new NotFoundException("IN returnBook() The user with id = " + userId +
                        " always return this book with id_publication = " + publicationId + " !!!");
            } else if(listExtradition.size() > listReturn.size()) {
                IssuanceLog issuanceLog = new IssuanceLog(StatusLog.RETURN.toString(),userLibrary, publication);
                issuanceLog.setDateReturn(Instant.now());
                issuanceLogRepository.save(issuanceLog);
                publication.setBookIssued(publication.getBookIssued() + 1);
                publicationRepository.save(publication);
                log.info("IN returnBook() The user with id={} return book with id_publication={} !!!",
                        userId, publicationId);
            }
        }
    //}
}
