package com.nhnacademy.ssackthree_aladdin_DB.repository;

import com.nhnacademy.ssackthree_aladdin_DB.domain.Author;
import com.nhnacademy.ssackthree_aladdin_DB.domain.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {
    Optional<Publisher> findByPublisherName(String publisherName);
}
