package com.github.alaverdyanani.msocial.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.time.LocalDate;

@Data
public class DailyDomainDto {
    @JsonProperty("domainname")
    private String domainName;

    private int hotness;

    private int price;

    @JsonProperty("x_value")
    private int xValue;

    @JsonProperty("yandex_tic")
    private int yandexTic;

    private int visitors;

    private String registrar;

    @JsonProperty("old")
    private int old;

    @JsonProperty("delete_date")
    private LocalDate deleteDate;

    private boolean rkn;

    private boolean judicial;

    private boolean block;

}
