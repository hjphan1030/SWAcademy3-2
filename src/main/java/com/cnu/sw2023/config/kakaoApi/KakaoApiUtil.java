package com.cnu.sw2023.config.kakaoApi;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class KakaoApiUtil {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static String restApiKey = "d22e8ce6be697663484467839c3dbcb7";

    public Map<String, List<Object>> getRestaurantInfo(){
        HashMap<String, List<Object>> info = new HashMap<>();
        for (int pageNum=1; pageNum<5;pageNum++) {
            String url = String.format("https://dapi.kakao.com/v2/local/search/category.json?category_group_code=FD6&x=127.35171762597&y=36.363894675137&radius=1500&page=%d", pageNum);
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "KakaoAK " + restApiKey);
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            String result = String.valueOf(response);
            String jsonString = result.substring(5, result.length() - 1);

            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(jsonString);
                System.out.println(jsonNode);
                JsonNode documentsNode = jsonNode.path("documents");

                for (JsonNode document : documentsNode) {
                    String addressName = document.path("road_address_name").asText();

                    if (addressName.contains("유성구")) { // 작성자 말고는 이해할수없게 되있어요 양해점,,
                        String placeName = document.path("place_name").asText();
                        String id = document.path("id").asText();
                        String phone = document.path("phone").asText();
                        String category = document.path("category_name").asText();
                        List<Object> list = new ArrayList<>();
                        list.add(addressName);
                        list.add(id);
                        list.add(phone);
                        list.add(placeName);
                        list.add(category);
                        info.put(id,list);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return info;
    }
}