package com.nhnacademy.ssackthree_aladdin_DB.service;

import com.nhnacademy.ssackthree_aladdin_DB.domain.APIData;
import com.nhnacademy.ssackthree_aladdin_DB.repository.APIRepository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class APIDataService {
    private final String API_LIST_URL = "http://www.aladin.co.kr/ttb/api/ItemList.aspx";
    private final String TTB_KEY = "ttbdbgus3150944001";
    private final String SEARCH_TARGET = "Book";
    private final String START_RESULT = "1";
    private final String MAX_RESULTS = "100";
    private final String OUTPUT = "js";
    private final String VERSION = "20131101";
    private final int MAX_LENGTH = 255;
    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final static RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private APIRepository apiRepository;

    public void saveBooks(String queryType) {
        String url = API_LIST_URL + "?ttbkey=" + TTB_KEY
            + "&QueryType=" + queryType
            + "&SearchTarget=" + SEARCH_TARGET
            + "&Start=" + START_RESULT
            + "&MaxResults=" + MAX_RESULTS
            + "&Output=" + OUTPUT
            + "&Version=" + VERSION;

        String response = restTemplate.getForObject(url, String.class);
        JSONObject jsonObject = new JSONObject(response);

        try {
            JSONArray items = jsonObject.getJSONArray("item");
            List<APIData> apiDataList = new ArrayList<>();

            for (int i = 0; i < items.length(); i++) {
                JSONObject item = items.getJSONObject(i);

                String title = item.getString("title");
                String author = item.getString("author");
                String pubDateString = item.getString("pubDate");
                LocalDate pubDate = LocalDate.parse(pubDateString, formatter);
                String description = item.getString("description");
                if (description.length() > MAX_LENGTH) {
                    description = description.substring(0, MAX_LENGTH);
                }

                String isbn13 = item.getString("isbn13");
                int priceSales = item.getInt("priceSales");
                int priceStandard = item.getInt("priceStandard");
                String cover = item.getString("cover");
                String categoryName = item.getString("categoryName");
                String publisher = item.getString("publisher");

                APIData apiData = new APIData();
                apiData.setTitle(title);
                apiData.setAuthor(author);
                apiData.setPubDate(pubDate);
                apiData.setDescription(description);
                apiData.setIsbn13(isbn13);
                apiData.setPriceSales(priceSales);
                apiData.setPriceStandard(priceStandard);
                apiData.setCover(cover);
                apiData.setCategoryName(categoryName);
                apiData.setPublisher(publisher);

                if (!apiRepository.existsByIsbn13(isbn13)) {
                    apiDataList.add(apiData);
                }
            }
            apiRepository.saveAll(apiDataList);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    // 특정 카테고리의 도서를 저장하는 메서드들
    public void saveBooksByBestseller() {
        saveBooks("Bestseller");
    }

    public void saveBooksByNewItemAll() {
        saveBooks("ItemNewAll");
    }
}
