package stalkervr.library.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
import stalkervr.library.service.IssuanceLogService;
import stalkervr.library.service.PublicationService;
import stalkervr.library.service.UserLibraryService;

import java.util.List;
import java.util.Optional;

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
    @RequestMapping(value = "new/", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserLibrary> addNewUser(@RequestBody @Validated UserLibrary userLibrary){
        HttpHeaders httpHeaders = new HttpHeaders();
        if(userLibrary == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        this.userLibraryService.addUser(userLibrary);
        return new ResponseEntity<>(userLibrary, httpHeaders, HttpStatus.OK);
    }

    /**
     * http://localhost:8085/api/users/all/
     */
    @RequestMapping(value = "all/", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Page<UserLibrary>> getAllUsers(@RequestParam Optional<Integer> page) {

        Page<UserLibrary> userLibraryPage = userLibraryService.getAllUserPage(
                PageRequest.of(
                        page.orElse(0),5
                )
        );
        return new ResponseEntity<>(userLibraryPage, HttpStatus.OK);
    }

    /**
     * http://localhost:8085/api/users/issuance/
     * http://localhost:8085/api/users/issuance/?page=0
     */
    @RequestMapping(value = "issuance/", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Page<IssuanceLog>> getAllIssuanceLog(@RequestParam Optional<Integer> page) {

        return new ResponseEntity<>
                (issuanceLogService.getAllIssuance(PageRequest.of(page.orElse(0),5)), HttpStatus.OK);
    }

    /**
     * http://localhost:8085/api/users/issuanceall/?page=0&userId=1
     * http://localhost:8085/api/users/issuanceall/?userId=1
     */
    @RequestMapping(value = "issuanceall/", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Page<IssuanceLog>> getAllIssuanceLogByUserId(
            @RequestParam Optional<Integer> page, @RequestParam Optional<Long> userId) {

        if(userId.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<IssuanceLog> issuanceLogList = issuanceLogService.getAllIssuanceByUserId(userId.get());

        Page<IssuanceLog> issuanceLogPage = new PageImpl<>(issuanceLogList,
                PageRequest.of(
                        page.orElse(0),5
                ), userId.orElse(1L)
        );
        return new ResponseEntity<>(issuanceLogPage, HttpStatus.OK);
    }

    /**
     * http://localhost:8085/api/users/take-book/?userId=2&publicationId=4
     */
    @RequestMapping(value = "take-book/", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<IssuanceLog>> takeBook(
            @RequestParam Optional<Long> userId, @RequestParam Optional<Long> publicationId) {

        if(userId.isEmpty() || publicationId.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        userLibraryService.takeBook(
                (userLibraryService.getUserById(userId.get())).get(),
                (publicationService.getPublicationById(publicationId.get())).get()
        );

        return new ResponseEntity<>( issuanceLogService.getAllIssuanceByUserId(userId.get()), HttpStatus.OK);
    }

    /**
     * http://localhost:8085/api/users/return-book/?userId=2&publicationId=4
     */
    @RequestMapping(value = "return-book/", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<IssuanceLog>> returnBook(
            @RequestParam Optional<Long> userId, @RequestParam Optional<Long> publicationId){

        if(userId.isEmpty() || publicationId.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        userLibraryService.returnBook(
                (userLibraryService.getUserById(userId.get())).get(),
                (publicationService.getPublicationById(publicationId.get())).get());

        return new ResponseEntity<>(issuanceLogService.getAllIssuanceByUserId(userId.get()), HttpStatus.OK);
    }
}
