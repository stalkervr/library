package stalkervr.library.controller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import stalkervr.library.entity.IssuanceLog;
import stalkervr.library.exception.BadRequestException;
import stalkervr.library.exception.NotFoundException;
import stalkervr.library.service.IssuanceLogService;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/issuances/")
public class IssuanceLogController {

    private final IssuanceLogService issuanceLogService;

    @Autowired
    public IssuanceLogController(IssuanceLogService issuanceLogService) {
        this.issuanceLogService = issuanceLogService;
    }

    /**
     * http://localhost:8085/api/issuances/all/
     * http://localhost:8085/api/issuances/all/?page=0
     */
    @SuppressWarnings("deprecation")
    @RequestMapping(value = "all/", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Page<IssuanceLog>> getAllIssuanceLog(@RequestParam Optional<Integer> page)
            throws NotFoundException {

        return new ResponseEntity<>
                (issuanceLogService.getAllIssuance(PageRequest.of(page.orElse(0),5)), HttpStatus.OK);
    }

    /**
     * http://localhost:8085/api/issuances/user/?userId=1
     * http://localhost:8085/api/issuances/user/?page=0&userId=1
     */
    @SuppressWarnings("deprecation")
    @RequestMapping(value = "user/", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Page<IssuanceLog>> getAllIssuanceLogByUserId(
            @RequestParam Optional<Integer> page, @RequestParam Optional<Long> userId)
            throws NotFoundException, BadRequestException {

        return new ResponseEntity<>
                (issuanceLogService.getAllIssuanceByUserId(page.orElse(0), userId.orElseThrow()), HttpStatus.OK);
    }
}
