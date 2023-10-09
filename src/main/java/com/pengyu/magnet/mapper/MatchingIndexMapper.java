package com.pengyu.magnet.mapper;

import com.pengyu.magnet.domain.Company;
import com.pengyu.magnet.domain.match.MatchingIndex;
import com.pengyu.magnet.dto.CompanyRequest;
import com.pengyu.magnet.dto.CompanyResponse;
import com.pengyu.magnet.dto.MatchingIndexDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MatchingIndexMapper {
    MatchingIndexMapper INSTANCE = Mappers.getMapper(MatchingIndexMapper.class);

    MatchingIndexDTO mapMatchingIndexToMatchingIndexDTO(MatchingIndex matchingIndex);
}
