package com.itunes.api.service;

import com.itunes.api.model.web.SearchRequest;
import com.itunes.api.model.web.SearchResponse;

public interface SearchService {

    SearchResponse getResponse(SearchRequest searchRequest);


}