package com.nhnacademy.ssackthree_aladdin_DB.repository;

import com.nhnacademy.ssackthree_aladdin_DB.domain.APIData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParserRepository extends JpaRepository<APIData, String> {
    boolean existsByIsbn13(String isbn13);
}
