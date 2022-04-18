package stalkervr.library.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import stalkervr.library.entity.Publication;
import stalkervr.library.entity.UserLibrary;
import stalkervr.library.exception.BadRequestException;
import stalkervr.library.exception.NotFoundException;

public interface UserLibraryService {

    void addUser(UserLibrary userLibrary) throws BadRequestException;

    UserLibrary getUserById(Long id) throws NotFoundException, BadRequestException;

    Page<UserLibrary> getAllUserPage(PageRequest pageRequest) throws NotFoundException;

    void takeBook(UserLibrary user, Publication publication) throws BadRequestException, NotFoundException;

    void returnBook(UserLibrary user, Publication publication) throws NotFoundException;
}
