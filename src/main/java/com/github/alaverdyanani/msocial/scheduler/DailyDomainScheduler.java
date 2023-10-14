package com.github.alaverdyanani.msocial.scheduler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.alaverdyanani.msocial.dto.DailyDomainDto;
import com.github.alaverdyanani.msocial.service.DailyDomainService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

@Component
public class DailyDomainScheduler {
    private static ObjectMapper objectMapper;
    private static String backorderUrl;
    private static DailyDomainService dailyDomainService;

    public DailyDomainScheduler(ObjectMapper objectMapper,
                                @Value("${backorder.url}") String backorderUrl,
                                DailyDomainService dailyDomainService) {
        DailyDomainScheduler.objectMapper = objectMapper;
        DailyDomainScheduler.backorderUrl = backorderUrl;
        DailyDomainScheduler.dailyDomainService = dailyDomainService;
    }

    public static int fetchAndUpdateData() throws IOException {
        List<DailyDomainDto> dailyDomainDtoList = objectMapper.readValue(
                new URL(backorderUrl),
                new TypeReference<LinkedList<DailyDomainDto>>() {
        });

        return dailyDomainService.updateAndGetCount(dailyDomainDtoList);
    }
}


