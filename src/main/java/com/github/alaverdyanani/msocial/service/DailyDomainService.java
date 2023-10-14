package com.github.alaverdyanani.msocial.service;

import com.github.alaverdyanani.msocial.dto.DailyDomainDto;
import com.github.alaverdyanani.msocial.mapper.DailyDomainMapper;
import com.github.alaverdyanani.msocial.repository.DailyDomainRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class DailyDomainService {
    private final DailyDomainRepository dailyDomainRepository;
    private final DailyDomainMapper dailyDomainMapper;

    @Transactional
    public int updateAndGetCount(List<DailyDomainDto> dailyDomainDtoList){
        dailyDomainRepository.deleteAll();
        for (DailyDomainDto dailyDomainDto : dailyDomainDtoList){
            dailyDomainRepository.save(dailyDomainMapper.toEntity(dailyDomainDto));
        }
        log.info("DB update completed");
        return (int)dailyDomainRepository.count();
    }
}
