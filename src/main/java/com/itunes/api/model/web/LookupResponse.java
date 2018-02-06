package com.itunes.api.model.web;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.itunes.api.model.Composition;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class LookupResponse {

    private Integer resultCount;
    private List<Composition> results;



}