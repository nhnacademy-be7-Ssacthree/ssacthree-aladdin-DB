package com.nhnacademy.ssacthree_aladdin_DB.repository;

import com.nhnacademy.ssacthree_aladdin_DB.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByBookIsbn(String isbn);
    boolean existsByBookIsbn(String isbn13);
}
