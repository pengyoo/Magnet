package com.pengyu.magnet.mapper;

import com.pengyu.magnet.domain.JobApplication;
import com.pengyu.magnet.dto.JobApplicationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface JobApplicationMapper {
    JobApplicationMapper INSTANCE = Mappers.getMapper(JobApplicationMapper.class);

    JobApplicationDTO mapJobApplicationToJobApplicationResponse(JobApplication jobApplication);
}
