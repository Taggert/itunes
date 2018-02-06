package com.itunes.api.service.impl;

import com.itunes.api.model.Composition;
import com.itunes.api.model.web.LookupRequest;
import com.itunes.api.model.web.LookupResponse;
import com.itunes.api.service.CashCheckService;
import com.itunes.api.service.LookupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CashCheckServiceImpl implements CashCheckService {

    public static Map<Composition, ArrayList<Composition>> result = new HashMap<>();

    @Autowired
    private LookupService lookupService;

    @Override
    public void getResponse(LookupRequest lr) {
        LookupResponse response = lookupService.getResponse(lr);
        Composition artist = response.getResults().get(0);
        List<Composition> compositions = response.getResults().subList(1, response.getResults().size());
        if (result.keySet().contains(artist)){
            result.get(artist).addAll(compositions);
        } else {
            ArrayList<Composition> compositions1 = new ArrayList<>();
            compositions1.addAll(compositions);
            result.put(artist, compositions1);
        }

    }
}