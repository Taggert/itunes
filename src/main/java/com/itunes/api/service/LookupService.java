package com.itunes.api.service;

import com.itunes.api.model.web.LookupRequest;
import com.itunes.api.model.web.LookupResponse;

public interface LookupService {

    LookupResponse getResponse(LookupRequest request);


}