package com.itunes.api.controller;

import com.itunes.api.model.Composition;
import com.itunes.api.model.EntityType;
import com.itunes.api.model.InputValidationException;
import com.itunes.api.model.web.*;
import com.itunes.api.service.CashCheckService;
import com.itunes.api.service.LookupService;
import com.itunes.api.service.SearchService;
import com.itunes.api.service.impl.CashCheckServiceImpl;
import com.itunes.api.service.impl.SearchServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @Autowired
    private LookupService lookupService;

    @Autowired
    private CashCheckService cashCheckService;

    @PostMapping("/query")
    public SearchResponse search(@RequestBody(required = false) @Valid SearchRequest sr, BindingResult result) {
        if (result.hasErrors()) {
            throw new InputValidationException(result);
        }
        //  long startTime = new Date().getTime();
        SearchResponse response = searchService.getResponse(sr);
       /* long stopTime = new Date().getTime();
        System.out.println("Time elapsed: " +(stopTime-startTime));*/
        return response;
    }

    @PostMapping("/lookup")
    public LookupResponse lookUpsearch(@RequestBody(required = false) @Valid LookupRequest lr, BindingResult result) {
        if (result.hasErrors()) {
            throw new InputValidationException(result);
        }
        LookupResponse response = lookupService.getResponse(lr);
        return response;
    }

    @GetMapping("/cashcheck")
    public CashCheckResponse cashCheck() {
        List<LookupRequest> requests = new ArrayList<>();
        for (SearchResponse sr : SearchServiceImpl.cashedMap.values()) {
            for (Composition composition : sr.getResults()) {
                if (composition.getArtistId() != null) {
                    requests.add(new LookupRequest(composition.getArtistId(), EntityType.getByName(composition.getKind())));
                }
            }
        }
        for (LookupRequest request : requests) {
            cashCheckService.getResponse(request);
        }
        CashCheckResponse response = new CashCheckResponse();
        response.setResult(CashCheckServiceImpl.result);

        return response;
    }

}