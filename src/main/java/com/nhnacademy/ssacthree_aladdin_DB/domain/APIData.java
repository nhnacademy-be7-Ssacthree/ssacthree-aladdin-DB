package com.nhnacademy.ssacthree_aladdin_DB.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "api_data")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class APIData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bookId;

    @NotNull
    private String title;
    @NotNull
    private String author;
    @NotNull
    private LocalDate pubDate;
    @NotNull
    private String description;
    @NotNull
    private String isbn13;
    @NotNull
    private int priceSales;//판매가
    @NotNull
    private int priceStandard; // 정가
    @NotNull
    private String cover; //URL
    @NotNull
    private String publisher;

}