package com.nhnacademy.ssackthree_aladdin_DB.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long publisherId;

    private String publisherName;

    private boolean publisherIsUsed;

    public Publisher(String publisherName, boolean publisherIsUsed) {
        this.publisherName = publisherName;
        this.publisherIsUsed = publisherIsUsed;
    }

    public Publisher(String publisherName){
        this.publisherName = publisherName;
    }

}
