package com.itunes.api.model.web;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.itunes.api.model.Composition;
import lombok.*;

import java.util.ArrayList;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class CashCheckResponse {

    private Map<Composition, ArrayList<Composition>> result;


}