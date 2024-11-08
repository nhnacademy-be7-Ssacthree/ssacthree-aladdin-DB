package com.nhnacademy.ssackthree_aladdin_DB.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "book")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bookId;
    private String bookName;
    private String bookIndex;
    private String bookInfo;
    private String bookIsbn;

    private LocalDate publicationDate;// LocalDateTime -> LocalDateTime으로 바꿨는데 괜찮나..?
    //    @Pattern(regexp = "\\d+", message = "가격은 숫자로 입력해주세요")
    private int regularPrice;
    //    @Pattern(regexp = "\\d+", message = "가격은 숫자로 입력해주세요")
    private int salePrice;
    private boolean isPacked;
    private int stock;
    private String bookThumbnailImageUrl;
    private int bookViewCount;
    private int bookDiscount;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private  Publisher publisher;

}
