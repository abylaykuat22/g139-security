package kz.bitlab.g139market.mapper;

import kz.bitlab.g139market.dto.UserCreateDto;
import kz.bitlab.g139market.dto.UserResponseDto;
import kz.bitlab.g139market.entity.User;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(builder = @Builder(disableBuilder = true))
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toEntity(UserCreateDto dto);
    UserResponseDto toDto(User user);
}
