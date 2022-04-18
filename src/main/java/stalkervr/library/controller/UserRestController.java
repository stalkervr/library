package stalkervr.library.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import stalkervr.library.entity.IssuanceLog;
import stalkervr.library.entity.UserLibrary;
import stalkervr.library.exception.BadRequestException;
import stalkervr.library.exception.NotFoundException;
import stalkervr.library.service.IssuanceLogService;
import stalkervr.library.service.PublicationService;
import stalkervr.library.service.UserLibraryService;

import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/users/")
public class UserRestController {

    private final UserLibraryService userLibraryService;
    private final IssuanceLogService issuanceLogService;
    private final PublicationService publicationService;

    @Autowired
    public UserRestController(UserLibraryService userLibraryService, IssuanceLogService issuanceLogService, PublicationService publicationService) {
        this.userLibraryService = userLibraryService;
        this.issuanceLogService = issuanceLogService;
        this.publicationService = publicationService;
    }

    /**
     * http://localhost:8085/api/users/new/
     * {
     *     "id": 3
     * }
     */
    @SuppressWarnings("deprecation")
    @RequestMapping(value = "new/", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserLibrary> addNewUser(@RequestBody @Validated UserLibrary userLibrary)
            throws BadRequestException {

        HttpHeaders httpHeaders = new HttpHeaders();
        this.userLibraryService.addUser(userLibrary);
        return new ResponseEntity<>(userLibrary, httpHeaders, HttpStatus.OK);
    }

    /**
     * http://localhost:8085/api/users/all/
     */
    @SuppressWarnings("deprecation")
    @RequestMapping(value = "all/", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Page<UserLibrary>> getAllUsers(@RequestParam Optional<Integer> page)
            throws NotFoundException {

        return new ResponseEntity<>(userLibraryService.getAllUserPage(
                PageRequest.of(page.orElse(0),5)), HttpStatus.OK);
    }

    /**
     * http://localhost:8085/api/users/take-book/?userId=2&publicationId=4
     */
    @SuppressWarnings("deprecation")
    @RequestMapping(value = "take-book/", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Page<IssuanceLog>> takeBook(@RequestParam Optional<Integer> page,
            @RequestParam Optional<Long> userId, @RequestParam Optional<Long> publicationId)
            throws NotFoundException, BadRequestException {

            userLibraryService.takeBook(
                    (userLibraryService.getUserById(userId.orElseThrow())),
                    (publicationService.getPublicationById(publicationId.orElseThrow())));

            return new ResponseEntity<>( issuanceLogService
                    .getAllIssuanceByUserId(page.orElse(0), userId.orElseThrow()), HttpStatus.OK);
    }

    /**
     * http://localhost:8085/api/users/return-book/?userId=2&publicationId=4
     */
    @SuppressWarnings("deprecation")
    @RequestMapping(value = "return-book/", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Page<IssuanceLog>> returnBook(@RequestParam Optional<Integer> page,
            @RequestParam Optional<Long> userId, @RequestParam Optional<Long> publicationId)
            throws NotFoundException, BadRequestException {

            userLibraryService.returnBook(
                    (userLibraryService.getUserById(userId.orElseThrow())),
                    (publicationService.getPublicationById(publicationId.orElseThrow())));
            return new ResponseEntity<>(issuanceLogService
                    .getAllIssuanceByUserId(page.orElse(0), userId.get()), HttpStatus.OK);
    }
}
