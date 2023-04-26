package com.mariesto.bookservice.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.mariesto.bookservice.common.NotFoundException;
import com.mariesto.bookservice.dto.BookDTO;
import com.mariesto.bookservice.dto.RatingDTO;
import com.mariesto.bookservice.persistence.entity.Book;
import com.mariesto.bookservice.persistence.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BookService {
    @Autowired
    private BookRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${rating-service.url}")
    private String ratingServiceUrl;

    public List<Book> findAllBook() {
        return repository.findAll();
    }

    public Book findByIsbn(String isbn) throws NotFoundException {
        return repository.findBookByIsbn(isbn).orElseThrow(() -> new NotFoundException("Book Not Found with isbn : " + isbn));
    }

    public void createBook(BookDTO bookDTO) {
        Book book = new Book();
        book.setIsbn(bookDTO.getIsbn());
        book.setAuthor(bookDTO.getAuthor());
        book.setTitle(bookDTO.getTitle());
        repository.save(book);
    }

    public void updateBook(BookDTO bookDTO, String isbn) {
        repository.findBookByIsbn(isbn).map(fetchedBook -> {
            fetchedBook.setTitle(bookDTO.getTitle());
            fetchedBook.setAuthor(bookDTO.getAuthor());
            fetchedBook.setIsbn(bookDTO.getIsbn());
            return repository.save(fetchedBook);
        });
    }

    public void deleteBook(String isbn) {
        repository.deleteBookByIsbn(isbn);
    }

    public void rateBook(String isbn, Integer stars) throws NotFoundException {
        final Book fetchedBook = findByIsbn(isbn);
        final RatingDTO ratingDTO = RatingDTO.builder().bookId(fetchedBook.getIsbn()).stars(stars).build();
        ResponseEntity<Void> responseEntity = restTemplate.postForEntity(ratingServiceUrl, ratingDTO, Void.class);
        responseEntity.getStatusCode();
    }

    public void rateBookWithError(String isbn, Integer stars) throws NotFoundException {
        final Book fetchedBook = findByIsbn(isbn);
        final RatingDTO ratingDTO = RatingDTO.builder().bookId(fetchedBook.getIsbn()).stars(stars).build();
        restTemplate.postForEntity(ratingServiceUrl + "simulate-error", ratingDTO, Void.class);
    }
}
