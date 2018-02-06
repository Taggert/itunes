package com.itunes.api.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itunes.api.model.web.SearchRequest;
import com.itunes.api.model.web.SearchResponse;
import com.itunes.api.service.SearchService;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Service
public class SearchServiceImpl implements SearchService {

    public static Map<SearchRequest, SearchResponse> cashedMap = new HashMap<>();

    @Override
    @SneakyThrows
    public SearchResponse getResponse(SearchRequest searchRequest) {

        SearchResponse response = cashedMap.getOrDefault(searchRequest, null);
        return response ==null ? getServerResponse(searchRequest):response;

    }

    @SneakyThrows
    private SearchResponse getServerResponse(SearchRequest searchRequest){
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://itunes.apple.com/search?";
        Field[] declaredFields = searchRequest.getClass().getDeclaredFields();
        Map<String, Object> urlParams = new HashMap<>();
        StringBuilder builder = new StringBuilder(url);
        for (Field field : declaredFields) {
            field.setAccessible(true);
            if(field.get(searchRequest)!=null){
                builder.append(field.getName()+"="+"{"+field.getName()+"}&");
                urlParams.put(field.getName(), field.get(searchRequest).toString().toLowerCase());
            }
            url = builder.substring(0,builder.length()-1);
        }
        ResponseEntity<String> entity = restTemplate.getForEntity(url, String.class, urlParams);

        String json = entity.getBody();
        SearchResponse searchResponse = new ObjectMapper().readValue(json, SearchResponse.class);
        cashedMap.put(searchRequest, searchResponse);
        return searchResponse;
    }
}