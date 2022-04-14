package stalkervr.library.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import stalkervr.library.entity.Publication;

import java.util.Optional;

public interface PublicationService {

    void addPublication(Publication publication);

    Optional<Publication> getPublicationById(Long id);

    Page<Publication> searchByText(String textForSearch, PageRequest pageRequest);

    Page<Publication> getAllPublicationSorted(PageRequest pageRequest);
}
