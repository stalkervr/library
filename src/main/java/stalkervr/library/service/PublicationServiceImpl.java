package stalkervr.library.service;

import lombok.extern.slf4j.Slf4j;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import stalkervr.library.entity.Publication;
import stalkervr.library.exception.BadRequestException;
import stalkervr.library.exception.NotFoundException;
import stalkervr.library.repository.PublicationRepository;

@Slf4j
@Service
public class PublicationServiceImpl implements PublicationService{

    private final PublicationRepository publicationRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public PublicationServiceImpl(PublicationRepository publicationRepository, EntityManager entityManager) {
        this.publicationRepository = publicationRepository;
        this.entityManager = entityManager;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void addPublication(Publication publication)
            throws BadRequestException{

        if(publication == null || publication.getName() == null){
            log.error("IN method addNewPublication() error create new publication(was sent empty body)");
            throw new BadRequestException("IN method addNewPublication() error create new publication(was sent empty body)");
        } else {
            publicationRepository.save(publication);
            log.info("IN PublicationServiceImpl addPublication() id - {}", publication.getId());
        }
    }

    @Override
    public Publication getPublicationById(Long id)
            throws NotFoundException, BadRequestException {

        if(id == null) {
            throw new BadRequestException("IN method getPublicationById(Long id) sent id == null !!!");
        }

        return publicationRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Publication with publicationId = " + id + " not found in library !!!"));
    }

    @Override
    public Page<Publication> getAllPublicationSorted(PageRequest pageRequest)
            throws NotFoundException {

        Page<Publication> publicationPage = publicationRepository.findAll(pageRequest);

        if(publicationPage.isEmpty()){
            log.warn("List publication is empty !!!");
            throw new NotFoundException("List publication is empty !!!");
        } else {
            return publicationRepository.findAll(pageRequest);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Page<Publication> searchByText(Integer page, String textForSearch, String sortBy)
            throws  BadRequestException {

        if(textForSearch.isEmpty()){
            log.warn("Not sent string for  search !!!");
            throw new BadRequestException("Not sent string for  search !!!");
        }

        FullTextEntityManager fullTextEntityManager
                = Search.getFullTextEntityManager(entityManager);

        try {
            fullTextEntityManager.createIndexer().startAndWait();
        } catch (InterruptedException e) {
            log.warn("IN method searchByText throws exception {}", e.getLocalizedMessage());
            //e.printStackTrace();
        }

        QueryBuilder queryBuilder = fullTextEntityManager
                .getSearchFactory()
                .buildQueryBuilder()
                .forEntity(Publication.class)
                .get();

        org.apache.lucene.search.Query   query = queryBuilder
                    .keyword()
                    .onFields("name", "description")
                    .matching(textForSearch)
                    .createQuery();


        org.hibernate.search.jpa.FullTextQuery jpaQuery
                = fullTextEntityManager.createFullTextQuery(query, Publication.class);

        if(!jpaQuery.hasPartialResults()){
            log.info("At this query not found publication !!!");
            //throw new NotFoundException("At this query not found publication !!!");
        }


        return new PageImpl<Publication>(
                jpaQuery.getResultList(),
                PageRequest.of(page,5, Sort.Direction.ASC, sortBy),
                0
        );
    }
}