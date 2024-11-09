package com.nhnacademy.ssackthree_aladdin_DB.repository;

import com.nhnacademy.ssackthree_aladdin_DB.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByCategoryName(String categoryName);
    Optional<Category> findByCategoryId(Long categoryId);
}
