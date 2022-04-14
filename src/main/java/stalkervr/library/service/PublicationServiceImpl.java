package stalkervr.library.service;

import lombok.extern.slf4j.Slf4j;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.Optional;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import stalkervr.library.entity.Publication;
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
    public void addPublication(Publication publication) {

        if(publication != null){
            publicationRepository.save(publication);
            log.info("IN PublicationServiceImpl addPublication() id - {}", publication.getId());
        } else {
            log.warn("IN PublicationServiceImpl addPublication() sent publication == null !!!");
        }
    }

    @Override
    public Optional<Publication> getPublicationById(Long id) {

        return publicationRepository.findById(id);
    }

    @Override
    public Page<Publication> getAllPublicationSorted(PageRequest pageRequest) {

        return publicationRepository.findAll(pageRequest);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<Publication> searchByText(String textForSearch, PageRequest pageRequest)  {

        FullTextEntityManager fullTextEntityManager
                = Search.getFullTextEntityManager(entityManager);

        try {
            fullTextEntityManager.createIndexer().startAndWait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        QueryBuilder queryBuilder = fullTextEntityManager
                    .getSearchFactory()
                    .buildQueryBuilder()
                    .forEntity(Publication.class)
                    .get();

        org.apache.lucene.search.Query query = queryBuilder
                .keyword()
                .onFields("name", "description")
                .matching(textForSearch)
                .createQuery();

        org.hibernate.search.jpa.FullTextQuery jpaQuery
                = fullTextEntityManager.createFullTextQuery(query, Publication.class);

        return new PageImpl<Publication>(jpaQuery.getResultList()) ;
    }
}
