package com.mariesto.bookservice.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.mariesto.bookservice.common.NotFoundException;
import com.mariesto.bookservice.dto.BookDTO;
import com.mariesto.bookservice.persistence.entity.Book;
import com.mariesto.bookservice.service.BookService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping ("/api/v1/books")
public class BookController {

    private final Logger LOG = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookService service;

    @GetMapping ("/")
    List<Book> findAllBook() {
        return service.findAllBook();
    }

    @GetMapping ("/{isbn}")
    Book findBookByIsbn(@PathVariable String isbn) throws NotFoundException {
        return service.findByIsbn(isbn);
    }

    @PostMapping ("/")
    @ResponseStatus (HttpStatus.CREATED)
    void createBook(@RequestBody BookDTO bookDTO) {
        service.createBook(bookDTO);
    }

    @PutMapping ("/{isbn}")
    @ResponseStatus (HttpStatus.OK)
    void updateBook(@RequestBody BookDTO bookDTO, @PathVariable String isbn) {
        service.updateBook(bookDTO, isbn);
    }

    @DeleteMapping ("/{isbn}")
    @ResponseStatus (HttpStatus.NO_CONTENT)
    void deleteBook(@PathVariable String isbn) {
        service.deleteBook(isbn);
    }

    @PostMapping ("/{isbn}/{stars}")
    @ResponseStatus (HttpStatus.OK)
    void rateBook(@PathVariable String isbn, @PathVariable Integer stars) throws NotFoundException {
        service.rateBook(isbn, stars);
    }

    ResponseEntity<HttpStatus> fallbackMethod(String isbn, Integer stars){
        LOG.error("Service Unavailable!!!");
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
    }

    @HystrixCommand(fallbackMethod = "fallbackMethod")
    @PostMapping ("/{isbn}/{stars}/simulate-error")
    @ResponseStatus (HttpStatus.CREATED)
    ResponseEntity<HttpStatus> rateBookWithError(@PathVariable String isbn, @PathVariable Integer stars) throws NotFoundException {
        service.rateBookWithError(isbn, stars);
        return ResponseEntity.ok().build();
    }
}
