package com.nhnacademy.ssacthree_aladdin_DB.controller;


import com.nhnacademy.ssacthree_aladdin_DB.service.ParserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class ParserController {

    private final ParserService parserService;

    @GetMapping("/Bestseller")
    public ResponseEntity<String> migrateBooksByBestSeller() {
        try{
            parserService.saveBooksByBestseller();
            return ResponseEntity.ok("도서 정보 마이그레이션 완료(베스트 셀러)");
        }catch(Exception e){
            log.error("Error during migration: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("실패");
        }

    }

    @GetMapping("/ItemNewAll")
    public ResponseEntity<String> migrateBooksByNewItemAll() {
        try{
            parserService.saveBooksByNewItemAll();
            return ResponseEntity.ok("도서 정보 마이그레이션 완료(신간도서)");
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("failed");
        }
    }


}
