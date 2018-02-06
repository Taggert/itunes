package com.itunes.api.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itunes.api.model.Composition;
import com.itunes.api.model.web.LookupRequest;
import com.itunes.api.model.web.LookupResponse;
import com.itunes.api.service.LookupService;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class LookupServiceImpl implements LookupService {

    static Map<Composition, List<Composition>> cashedMap = new HashMap<>();

    @Override
    public LookupResponse getResponse(LookupRequest request) {
        Composition artist = null;
        for (Composition a : cashedMap.keySet()) {
            if(a.getArtistId().equals(request.getId())){
                artist =a;
                break;
            }
        }
       if(artist == null){
            return getServerResponse(request);
       }else{
           List<Composition> list = new ArrayList<>();
           list.add(artist);
           LookupResponse response = new LookupResponse(1, list);
           return  response;
       }
    }
    @SneakyThrows
    private LookupResponse getServerResponse(LookupRequest request) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://itunes.apple.com/lookup?";
        Field[] declaredFields = request.getClass().getDeclaredFields();
        Map<String, Object> urlParams = new HashMap<>();
        StringBuilder builder = new StringBuilder(url);
        for (Field field : declaredFields) {
            field.setAccessible(true);
            if(field.get(request)!=null){
                builder.append(field.getName()+"="+"{"+field.getName()+"}&");
                urlParams.put(field.getName(), field.get(request).toString().toLowerCase());
            }
            url = builder.substring(0,builder.length()-1);
        }
        ResponseEntity<String> entity = restTemplate.getForEntity(url, String.class, urlParams);

        String json = entity.getBody();
        LookupResponse response = new ObjectMapper().readValue(json, LookupResponse.class);
        cashedMap.putIfAbsent(response.getResults().get(0), response.getResults().subList(1, response.getResults().size()));
            return response;

    }
}