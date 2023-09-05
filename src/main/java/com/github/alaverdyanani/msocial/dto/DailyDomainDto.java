package com.github.alaverdyanani.msocial.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDate;
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class DailyDomainDto {
    @JsonProperty("domainname")
    private String domainName;

    @JsonProperty("hotness")
    private int hotness;

    @JsonProperty("price")
    private int price;

    @JsonProperty("x_value")
    private int xValue;
    @JsonProperty("yandex_tic")
    private int yandexTic;

    @JsonProperty("links")
    private int links;

    @JsonProperty("visitors")
    private int visitors;

    @JsonProperty("registrar")
    private String registrar;

    @JsonProperty("old")
    private int old;

    @JsonProperty("delete_date")
    private LocalDate deleteDate;

    @JsonProperty("rkn")
    private boolean rkn;

    @JsonProperty("judicial")
    private boolean judicial;

    @JsonProperty("block")
    private boolean block;

}
