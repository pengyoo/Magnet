package com.pengyu.magnet.mapper;

import com.pengyu.magnet.domain.Company;
import com.pengyu.magnet.domain.Job;
import com.pengyu.magnet.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface JobMapper {
    JobMapper INSTANCE = Mappers.getMapper(JobMapper.class);

    JobResponse mapJobToJobResponse(Job job);
    Job mapJobRequestToJob(JobRequest jobRequest);
}
