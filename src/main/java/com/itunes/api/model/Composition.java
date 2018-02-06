package com.itunes.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Composition {

    private String kind;
    private String artistName;
    private String trackName;
    private Double trackPrice;
    private Double price;
    private String currency;
    private String artistType;
    private String artistLinkUrl;
    private String primaryGenreName;
    private Integer artistId;




}