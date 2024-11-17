package com.nhnacademy.ssacthree_aladdin_DB.repository;

import com.nhnacademy.ssacthree_aladdin_DB.domain.BookAuthor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookAuthorRepository extends JpaRepository<BookAuthor, Long> {
}
