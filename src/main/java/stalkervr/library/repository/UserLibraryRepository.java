package stalkervr.library.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import org.springframework.stereotype.Repository;
import stalkervr.library.entity.UserLibrary;

@Repository
public interface UserLibraryRepository extends PagingAndSortingRepository<UserLibrary, Long> {
}