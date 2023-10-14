package com.github.alaverdyanani.msocial.mapper;

import com.github.alaverdyanani.msocial.dto.DailyDomainDto;
import com.github.alaverdyanani.msocial.entity.DailyDomain;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DailyDomainMapper {

    DailyDomain toEntity(DailyDomainDto dailyDomainDto);
}
