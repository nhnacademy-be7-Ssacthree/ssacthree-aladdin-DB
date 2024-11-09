package com.nhnacademy.ssackthree_aladdin_DB.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    private long authorId;
    private String authorName;
    private String authorInfo;

    public Author(String authorName, String authorInfo) {
        this.authorName = authorName;
        this.authorInfo = authorInfo;
    }

}
