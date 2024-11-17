package com.nhnacademy.ssacthree_aladdin_DB.repository;

import com.nhnacademy.ssacthree_aladdin_DB.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByAuthorName(String authorName);
}
