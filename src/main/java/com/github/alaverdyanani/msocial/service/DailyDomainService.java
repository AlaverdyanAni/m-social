package com.github.alaverdyanani.msocial.service;

import com.github.alaverdyanani.msocial.client.BackorderClient;
import com.github.alaverdyanani.msocial.dto.DailyDomainDto;
import com.github.alaverdyanani.msocial.entity.DailyDomain;
import com.github.alaverdyanani.msocial.entity.Message;
import com.github.alaverdyanani.msocial.entity.User;
import com.github.alaverdyanani.msocial.mapper.DailyDomainMapper;
import com.github.alaverdyanani.msocial.repository.DailyDomainRepository;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class DailyDomainService {
    private final DailyDomainRepository dailyDomainRepository;
    private final DailyDomainMapper dailyDomainMapper;
    private final BackorderClient backorderClient;

    public void saveDailyDomains() throws IOException {
        List<DailyDomainDto> dailyDomainDtos = backorderClient.read();
        dailyDomainRepository.saveAll(dailyDomainDtos
                .stream()
                .map(dailyDomainMapper::toEntity)
                .collect(Collectors.toList()));
        log.info("List Сохранен в базе данных");
    }

    public List<DailyDomainDto> sendDailyDomains() {
        List<DailyDomain> dailyDomains = dailyDomainRepository.findAll();
        return dailyDomains
                .stream()
                .map(dailyDomainMapper::toDto)
                .collect(Collectors.toList());
    }
}
