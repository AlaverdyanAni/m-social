package com.github.alaverdyanani.msocial.mapper;

import com.github.alaverdyanani.msocial.dto.DailyDomainDto;
import com.github.alaverdyanani.msocial.entity.DailyDomain;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.context.annotation.Bean;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DailyDomainMapper {

    DailyDomainDto toDto(DailyDomain dailyDomain);

    DailyDomain toEntity(DailyDomainDto dailyDomainDto);
}
