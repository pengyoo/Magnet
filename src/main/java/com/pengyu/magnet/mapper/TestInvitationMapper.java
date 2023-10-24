package com.pengyu.magnet.mapper;

import com.pengyu.magnet.domain.assessment.TestInvitation;
import com.pengyu.magnet.dto.TestInvitationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TestInvitationMapper {
    TestInvitationMapper INSTANCE = Mappers.getMapper(TestInvitationMapper.class);

    TestInvitationDTO mapTestInvitationToTestInvitationResponse(TestInvitation testInvitation);

}
