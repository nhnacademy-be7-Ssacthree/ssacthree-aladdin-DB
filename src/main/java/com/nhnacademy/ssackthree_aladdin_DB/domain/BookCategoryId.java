package com.nhnacademy.ssackthree_aladdin_DB.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class BookCategoryId implements Serializable {
    private long bookId;
    private long categoryId;


}
