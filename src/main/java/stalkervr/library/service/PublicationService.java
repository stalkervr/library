package stalkervr.library.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import stalkervr.library.entity.Publication;
import stalkervr.library.exception.BadRequestException;
import stalkervr.library.exception.NotFoundException;

public interface PublicationService {

    void addPublication(Publication publication) throws BadRequestException;

    Publication getPublicationById(Long id) throws NotFoundException, BadRequestException;

    Page<Publication> searchByText(Integer page, String textForSearch, String sortBy) throws BadRequestException;

    Page<Publication> getAllPublicationSorted(PageRequest pageRequest) throws NotFoundException;
}
