package com.github.alaverdyanani.msocial.entity;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "daily_domains")
public class DailyDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String domainName;

    private int hotness;

    private int price;

    private int xValue;

    private int yandexTic;

    private int links;

    private int visitors;

    private String registrar;

    private int old;

    private LocalDate deleteDate;

    private boolean rkn;

    private boolean judicial;

    private boolean block;

}
