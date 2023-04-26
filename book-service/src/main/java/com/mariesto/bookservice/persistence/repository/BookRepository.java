package com.mariesto.bookservice.persistence.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.mariesto.bookservice.persistence.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findBookByIsbn(final String isbn);

    void deleteBookByIsbn(final String isbn);
}
