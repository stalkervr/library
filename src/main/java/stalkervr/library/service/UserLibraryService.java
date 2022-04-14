package stalkervr.library.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import stalkervr.library.entity.IssuanceLog;
import stalkervr.library.entity.Publication;
import stalkervr.library.entity.UserLibrary;

import java.util.List;
import java.util.Optional;

public interface UserLibraryService {

    void addUser(UserLibrary userLibrary);

    Optional<UserLibrary> getUserById(Long id);

    //List<UserLibrary> getAllUsers();

    Page<UserLibrary> getAllUserPage(PageRequest pageRequest);

    void takeBook(UserLibrary user, Publication publication);

    void returnBook(UserLibrary user, Publication publication);
}
