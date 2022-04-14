package stalkervr.library.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import org.springframework.stereotype.Repository;
import stalkervr.library.entity.Publication;

@Repository
public interface PublicationRepository extends PagingAndSortingRepository<Publication, Long> {
}