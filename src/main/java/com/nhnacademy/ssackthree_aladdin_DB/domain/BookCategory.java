package com.nhnacademy.ssackthree_aladdin_DB.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookCategory {
    @EmbeddedId
    private BookCategoryId bookCategoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("bookId")
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("categoryId")
    @JoinColumn(name = "category_id")
    private Category category;

    public BookCategory(Book book, Category category) {
        this.book = book;
        this.category = category;
        this.bookCategoryId = new BookCategoryId(book.getBookId(), category.getCategoryId());
    }
}
