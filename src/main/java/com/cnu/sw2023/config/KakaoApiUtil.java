package com.cnu.sw2023.config;


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

    public String requestToKakaoApi(String url, int N){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + restApiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        String result = String.valueOf(response);
        return result.substring(N, result.length() - 1);
    }
    public HashMap<String, List<Object>> saveRestaurantInfoByCoordinate(String x, String y){
        HashMap<String, List<Object>> info = new HashMap<>();
        for (int pageNum=1; pageNum<45;pageNum++) {
            String responseJasonString1 = requestToKakaoApi(String.format("https://dapi.kakao.com/v2/local/search/category.json?category_group_code=FD6&x=%s&y=%s&radius=6000&page=%s",x,y, pageNum), 5);
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode1 = objectMapper.readTree(responseJasonString1);
                JsonNode documentsNode1 = jsonNode1.path("documents");
                for (JsonNode document1 : documentsNode1) {
                    String addressName = document1.path("road_address_name").asText();
                    String X = document1.path("x").asText();
                    String Y = document1.path("y").asText();
                    String responseJasonString2 = requestToKakaoApi(String.format("https://dapi.kakao.com/v2/local/geo/coord2regioncode.json?x=%s&y=%s", X, Y), 5);
                    JsonNode jsonNode2 = objectMapper.readTree(responseJasonString2);
                    JsonNode documentsNode2 = jsonNode2.path("documents");
                    String output = documentsNode2.toString();
                    documentsNode2 = new ObjectMapper().readTree(output.substring(1, output.length()-1));
                    String region = documentsNode2.path("region_3depth_name").asText();

                    if (region.equals("궁동")) {
                        String placeName = document1.path("place_name").asText();
                        String id = document1.path("id").asText();
                        String phone = document1.path("phone").asText();
                        String category = document1.path("category_name").asText();
                        List<Object> list = new ArrayList<>();
                        list.add(addressName);
                        list.add(id);
                        list.add(phone);
                        list.add(placeName);
                        if(category.contains("한식")){category = "한식";}
                        else if(category.contains("일식")){category = "일식";}
                        else if(category.contains("중식")){category = "중식";}
                        else if(category.contains("양식")){category = "양식";}
                        else{category = "기타";}
                        list.add(category);
                        list.add(region);
                        info.put(id,list);
                    }
                    else if (region.equals("봉명동")) {
                        String placeName = document1.path("place_name").asText();
                        String id = document1.path("id").asText();
                        String phone = document1.path("phone").asText();
                        String category = document1.path("category_name").asText();
                        List<Object> list = new ArrayList<>();
                        list.add(addressName);
                        list.add(id);
                        list.add(phone);
                        list.add(placeName);
                        if(category.contains("한식")){category = "한식";}
                        else if(category.contains("일식")){category = "일식";}
                        else if(category.contains("중식")){category = "중식";}
                        else if(category.contains("양식")){category = "양식";}
                        else{category = "기타";}
                        list.add(category);
                        list.add(region);
                        info.put(id,list);
                    }
                    else if (region.equals("어은동")) {
                        String placeName = document1.path("place_name").asText();
                        String id = document1.path("id").asText();
                        String phone = document1.path("phone").asText();
                        String category = document1.path("category_name").asText();
                        List<Object> list = new ArrayList<>();
                        list.add(addressName);
                        list.add(id);
                        list.add(phone);
                        list.add(placeName);
                        if(category.contains("한식")){category = "한식";}
                        else if(category.contains("일식")){category = "일식";}
                        else if(category.contains("중식")){category = "중식";}
                        else if(category.contains("양식")){category = "양식";}
                        else{category = "기타";}
                        list.add(category);
                        list.add(region);
                        info.put(id,list);
                    }
                    else if (region.equals("죽동")) {
                        String placeName = document1.path("place_name").asText();
                        String id = document1.path("id").asText();
                        String phone = document1.path("phone").asText();
                        String category = document1.path("category_name").asText();
                        List<Object> list = new ArrayList<>();
                        list.add(addressName);
                        list.add(id);
                        list.add(phone);
                        list.add(placeName);
                        if(category.contains("한식")){category = "한식";}
                        else if(category.contains("일식")){category = "일식";}
                        else if(category.contains("중식")){category = "중식";}
                        else if(category.contains("양식")){category = "양식";}
                        else{category = "기타";}
                        list.add(category);
                        list.add(region);
                        info.put(id,list);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return info;
    }
    public Map<String, List<Object>> getRestaurantInfo(){
        HashMap<String, List<Object>> map_1 =  saveRestaurantInfoByCoordinate("127.35171762597","36.363894675137");
        HashMap<String, List<Object>> map_2 =  saveRestaurantInfoByCoordinate("127.346041","36.353901");
        HashMap<String, List<Object>> map_3 =  saveRestaurantInfoByCoordinate("127.337227","36.369694");
        map_1.putAll(map_2);
        map_1.putAll(map_3);
        return map_1;
    }
}