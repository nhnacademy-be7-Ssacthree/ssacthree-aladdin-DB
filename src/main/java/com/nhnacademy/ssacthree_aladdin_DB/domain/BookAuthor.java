package com.nhnacademy.ssacthree_aladdin_DB.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookAuthor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="book_author_id")
    private long bookAuthorId;

    @ManyToOne
    @JoinColumn(name="authorId")
    @NotNull
    private Author author;

    @ManyToOne
    @JoinColumn(name="bookId")
    @NotNull
    private Book book;

    public BookAuthor(Book book, Author author){
        this.book = book;
        this.author = author;
    }
}
