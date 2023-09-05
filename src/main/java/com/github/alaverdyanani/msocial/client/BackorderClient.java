package com.github.alaverdyanani.msocial.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.alaverdyanani.msocial.dto.DailyDomainDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.net.URL;
import java.util.List;

@Component
public class BackorderClient {
    private  final ObjectMapper objectMapper;
    private  final  String backorderUrl;

    public BackorderClient(ObjectMapper objectMapper,
                           @Value("${application.backorder.url}") String backorderUrl) {
        this.objectMapper = objectMapper;
        this.backorderUrl = backorderUrl;
    }
    @Scheduled(cron = "@daily")
    public List<DailyDomainDto> read() throws IOException {
        return objectMapper.readValue(new URL(backorderUrl), new TypeReference<List<DailyDomainDto>>(){});
    }
}
