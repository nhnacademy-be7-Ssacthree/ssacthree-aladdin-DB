package com.nhnacademy.ssacthree_aladdin_DB.service;

import com.nhnacademy.ssacthree_aladdin_DB.domain.*;
import com.nhnacademy.ssacthree_aladdin_DB.repository.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.HtmlUtils;

@Service
@Slf4j
@Transactional
public class ParserService {
    private final List<Integer> aladdinCategoryList = List.of(51365,51355,51160,51095,4669,4671,53820,2559,89481,89482,57750,57745,57753,57942,51423,51843,51844,103882,103883,103906,103907,51541,8521,51493 ,51500,104391,104387,70229,70230,6369,387,50994,50993); //알라딘에서 지정한 CategoryId
    private final List<Integer> dbCategoryList = List.of(25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,57,58); //우리 db에서 지정한 CateogryId
    //"공학 일반", "기계공학", "과학고전", "과학사/기술사", "시사/풍자만화","실용만화","기타 만화 이야기", "만화비평/만화이론", "외국 과학소설", "한국 과학소설","기타문학상","소월시문학상","노벨문학상","국내여행","국내여행에세이","미술에세이","영화에세이","그리스","로마","16세기","17세기","기호학","언어학", "도가철학/노장철학","동양철학 일반","Hoarding", "강박증(OCD)","기획","보고","네트워크 구축","네트워크 보안/해킹","2000년대 이후 한국 소설","2000년대 이전 한국 소설" //Category Nam

    private static int PASSEDCOUNT = 0;
    private final String API_LIST_URL = "http://www.aladin.co.kr/ttb/api/ItemList.aspx";
    private final String TTB_KEY = "ttbdbgus3150944001";
    private final String SEARCH_TARGET = "ALL";//도서, 외국도서,음반 등등 몰 전체
    private final String START_RESULT = "1";
    private final String MAX_RESULTS = "100";
    private final String BIG = "big";
    private final String OUTPUT = "js";
    private final String VERSION = "20131101";
    private final int MAX_LENGTH = 255;
    private final int DEFAULT_VALUE = 0;
    private final String BOOK_INDEX = "목차";
    private final String AUTHOR = "작가";
    private final String NO_INFO_MESSAGE = " 정보가 없습니다.";
    private final String EXISTING_CATEGORY_ID = " 카테고리는 DB에 존재하지 않습니다.";
    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final static RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private PublisherRepository publisherRepository;
    @Autowired
    private BookAuthorRepository bookAuthorRepository;
    @Autowired
    private BookCategoryRepository bookCategoryRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookCategoryIdRepository bookCategoryIdRepository;

    public void saveBooks(String queryType) {

        try {
            for(int i = 0; i < dbCategoryList.size(); i++) {
                String url = API_LIST_URL
                        + "?ttbkey=" + TTB_KEY
                        + "&QueryType=" + queryType
                        + "&CategoryId=" + aladdinCategoryList.get(i)
                        + "&SearchTarget=" + SEARCH_TARGET
                        + "&Start=" + START_RESULT
                        + "&MaxResults=" + MAX_RESULTS
                        + "&Cover=" + BIG
                        + "&Output=" + OUTPUT
                        + "&Version=" + VERSION;

                String response = restTemplate.getForObject(url, String.class);
                JSONObject jsonObject = new JSONObject(response);

                JSONArray items = jsonObject.getJSONArray("item");
                for(int j = 0; j < items.length(); j++) {
                    JSONObject item = items.getJSONObject(j);

                    String title = item.getString("title");
                    String authorName = item.getString("author").trim();
                    String pubDateString = item.getString("pubDate");
                    LocalDate pubDate = LocalDate.parse(pubDateString, formatter);
                    String description = item.getString("description"); //책 설명
                    if (description.length() > MAX_LENGTH) {
                        description = description.substring(DEFAULT_VALUE, MAX_LENGTH);
                    }

                    String isbn13 = item.getString("isbn13");
                    if (bookRepository.existsByBookIsbn(isbn13)) {
                        log.info("Duplicate ISBN found: {}", isbn13);  // 디버그용
                        continue;
                    }
                    int priceSales = item.getInt("priceSales"); //판매가
                    int priceStandard = item.getInt("priceStandard"); //정가
                    String cover = item.getString("cover"); //url 주소
                    String publisherName = item.getString("publisher").trim();
                    log.info("Publisher Name: {}", publisherName); // 디버그용

                    //카테고리 저장
                    Long dbCategoryId = Long.valueOf(dbCategoryList.get(i));
                    Category categoryResult = categoryRepository.findByCategoryId(dbCategoryId)
                            .orElseThrow(() -> new IllegalArgumentException(dbCategoryId + EXISTING_CATEGORY_ID));

                    //출판사 저장
                    if(publisherName.length() >= 30){
                        PASSEDCOUNT++;
                        continue;
                    }
                    Publisher publisherResult = publisherRepository.findByPublisherName(publisherName)
                            .orElseGet(() -> {
                                Publisher publisher = new Publisher(publisherName, true);
                                return publisherRepository.save(publisher);
                            });

                    Book book = new Book();
                    book.setBookName(title);
                    book.setBookIndex(BOOK_INDEX + NO_INFO_MESSAGE);
                    book.setBookInfo(decode(description));
                    book.setBookIsbn(isbn13);
                    book.setPublicationDate(pubDate);
                    book.setRegularPrice(priceStandard);
                    book.setSalePrice(priceSales);
                    book.setPacked(true);
                    book.setStock(100);

                    String cover500 = cover.replace("cover200", "cover500").trim();
                    book.setBookThumbnailImageUrl(cover500);
                    book.setBookViewCount(DEFAULT_VALUE);
                    book.setBookDiscount(calculateDiscount(priceStandard, priceSales));
                    book.setPublisher(publisherResult);

                    if(authorName.length() >= 20){
                        PASSEDCOUNT++;
                        continue;
                    }

                    String newAuthorName = distinguishAuthors(authorName);
                    Author authorResult = authorRepository.findByAuthorName(newAuthorName)
                            .orElseGet(() -> {
                                Author author = new Author(newAuthorName, AUTHOR + NO_INFO_MESSAGE);
                                return authorRepository.save(author);
                            });
                    log.info(authorName);
                    try {
                        Book bookResult = bookRepository.save(book);
                        bookAuthorRepository.save(new BookAuthor(bookResult, authorResult));
                        BookCategory bookCategory = new BookCategory(bookResult, categoryResult);
                        bookCategoryRepository.save(bookCategory);
                    } catch (DataIntegrityViolationException e) {
                        log.info("Duplicate ISBN found: {}" + isbn13);
                    }

                }
                log.info("{}번째 카테고리",i);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        log.info("{}개 passed", PASSEDCOUNT);
    }

    public void saveBooksByBestseller() {
        saveBooks("Bestseller");
    }

    public void saveBooksByNewItemAll() {
        saveBooks("ItemNewAll");
    }

    private String distinguishAuthors(String input){
        String[] authorsParts = input.split(",");
        return authorsParts[0].replaceAll("\\(.*?\\)","").trim();
    }

    public String decode(String encode){
        return HtmlUtils.htmlUnescape(encode);
    }

    private int calculateDiscount(int regularPrice, int salePrice) {
        return (int) ((regularPrice - salePrice) * 100.0 / regularPrice);
    }

}
