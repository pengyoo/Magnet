package com.pengyu.magnet.mapper;

import com.pengyu.magnet.domain.Company;
import com.pengyu.magnet.domain.JobApplication;
import com.pengyu.magnet.dto.CompanyRequest;
import com.pengyu.magnet.dto.CompanyResponse;
import com.pengyu.magnet.dto.JobApplicationResponse;
import com.pengyu.magnet.repository.JobApplicationRepository;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface JobApplicationMapper {
    JobApplicationMapper INSTANCE = Mappers.getMapper(JobApplicationMapper.class);

    JobApplicationResponse mapJobApplicationToJobApplicationResponse(JobApplication jobApplication);
}
