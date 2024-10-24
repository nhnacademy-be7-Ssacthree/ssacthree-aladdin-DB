package com.nhnacademy.ssackthree_aladdin_DB.controller;

import com.nhnacademy.ssackthree_aladdin_DB.service.APIDataService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/books")
public class APIController {

    @Autowired
    private APIDataService apiDataService;

    @GetMapping("/Bestseller")
    public ResponseEntity<String> migrateBooksByBestSeller() {
        apiDataService.saveBooksByBestseller();
        return ResponseEntity.ok("도서 정보 마이그레이션 완료(베스트 셀러)");
    }

    @GetMapping("/NewItemAll")
    public ResponseEntity<String> migrateBooksByNewItemAll() {
        apiDataService.saveBooksByNewItemAll();
        return ResponseEntity.ok("도서 정보 마이그레이션 완료(신간도서)");
    }


}
