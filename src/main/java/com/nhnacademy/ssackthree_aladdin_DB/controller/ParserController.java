package com.nhnacademy.ssackthree_aladdin_DB.controller;

import com.nhnacademy.ssackthree_aladdin_DB.service.ParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class ParserController {

    @Autowired
    private ParserService parserService;

    @GetMapping("/Bestseller")
    public ResponseEntity<String> migrateBooksByBestSeller() {
        try{
            parserService.saveBooksByBestseller();
            return ResponseEntity.ok("도서 정보 마이그레이션 완료(베스트 셀러)");
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("실패");
        }

    }

    @GetMapping("/ItemNewAll")
    public ResponseEntity<String> migrateBooksByNewItemAll() {
        try{
            parserService.saveBooksByNewItemAll();
            return ResponseEntity.ok("도서 정보 마이그레이션 완료(신간도서)");
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("faileld");
        }
    }


}
