package com.nhnacademy.ssacthree_aladdin_DB.repository;

import com.nhnacademy.ssacthree_aladdin_DB.domain.BookCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookCategoryIdRepository extends JpaRepository<BookCategory, Long> {
}
