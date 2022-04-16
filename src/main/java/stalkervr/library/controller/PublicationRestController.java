package stalkervr.library.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

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

import stalkervr.library.entity.Publication;
import stalkervr.library.service.PublicationService;

import java.util.Optional;

@RestController
@RequestMapping("/api/publications/")
public class PublicationRestController {

    private final PublicationService publicationService;

    @Autowired
    public PublicationRestController(PublicationService publicationService) {
        this.publicationService = publicationService;
    }

    /**
     * http://localhost:8085/api/publications/new/
     * {
     *     "name":"Anna Karenina",
     *     "description": "Story about Anna Karenina",
     *     "bookCount": 5
     * }
     */
    @SuppressWarnings("deprecation")
    @RequestMapping(value = "new/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Publication> addNewPublication(@RequestBody @Validated Optional<Publication> publication) {
        HttpHeaders httpHeaders = new HttpHeaders();
        if(publication.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        publication.get().setBookIssued(publication.get().getBookCount());
        this.publicationService.addPublication(publication.get());
        return new ResponseEntity<>(publication.get(), httpHeaders, HttpStatus.CREATED);
    }

    /**
     * http://localhost:8085/api/publications/?publicationId=1
     */
    @SuppressWarnings("deprecation")
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Publication> getPublication(@RequestParam Optional<Long>  publicationId) {

        if(publicationId.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<Publication> publication = publicationService.getPublicationById(publicationId.get());
        return publication.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * http://localhost:8085/api/publications/all/
     * http://localhost:8085/api/publications/all/?page=0
     * http://localhost:8085/api/publications/all/?sortBy=name
     * http://localhost:8085/api/publications/all/?page=0&sortBy=name
     */
    @SuppressWarnings("deprecation")
    @RequestMapping(value = "all/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Page<Publication>> getPublications(@RequestParam Optional<Integer> page, @RequestParam Optional<String> sortBy){

        Page<Publication> publicationPage = publicationService.getAllPublicationSorted(
                PageRequest.of(
                page.orElse(0), 5,
                Sort.Direction.ASC, sortBy.orElse("id")
                )
        );
        return new ResponseEntity<>(publicationPage, HttpStatus.OK);
    }

    /**
     * http://localhost:8085/api/publications/all/search/?searchText=Anna Karenina
     * http://localhost:8085/api/publications/all/search/?searchText=Портрет Дориана Грея
     */
    @SuppressWarnings("deprecation")
    @RequestMapping(value = "all/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Page<Publication>> searchByTextNew (
            @RequestParam Optional<Integer> page, @RequestParam Optional<String> searchText,
            @RequestParam Optional<String> sortBy){

        if(searchText.isEmpty() || searchText.get().equals("")){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(
                publicationService.searchByText(page.orElse(0), searchText.orElse(""), sortBy.orElse("id")),
                HttpStatus.OK
        );
    }
}