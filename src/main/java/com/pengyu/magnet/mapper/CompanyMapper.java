package com.pengyu.magnet.mapper;

import com.pengyu.magnet.domain.Company;
import com.pengyu.magnet.dto.CompanyRequest;
import com.pengyu.magnet.dto.CompanyResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CompanyMapper {
    CompanyMapper INSTANCE = Mappers.getMapper(CompanyMapper.class);

    CompanyResponse mapCompanyToCompanyResponse(Company company);
    Company mapCompanyRequestToCompany(CompanyRequest company);
}
