package com.pengyu.magnet.mapper;

import com.pengyu.magnet.domain.User;
import com.pengyu.magnet.dto.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserResponse mapUserToUserResponse(User user);
}
