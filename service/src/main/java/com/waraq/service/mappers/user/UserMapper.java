package com.waraq.service.mappers.user;

import com.waraq.dto.admin.UserResponse;
import com.waraq.dto.admin.CreateUserRequest;
import com.waraq.helpers.UserLoader;
import com.waraq.repository.entities.user.UserEntity;
import com.waraq.repository.entities.user.WaraqUserEntity;
import com.waraq.service.mappers.BaseMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

public class UserMapper implements BaseMapper<WaraqUserEntity, CreateUserRequest, UserResponse> {

    private final BCryptPasswordEncoder encoder;

    public UserMapper(BCryptPasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public WaraqUserEntity mapCreateDtoToEntity(CreateUserRequest createDto) {
        final LocalDateTime now = LocalDateTime.now();
        return WaraqUserEntity.builder()
                .user(UserEntity.builder()
                        .firstName(createDto.getFirstName())
                        .lastName(createDto.getLastName())
                        .email(createDto.getEmail().toLowerCase())
                        .username(createDto.getEmail().toLowerCase())
                        .phoneNumber(createDto.getPhoneNumber())
                        .password(encoder.encode(createDto.getPassword()))
                        .createdBy(UserLoader.userDetails().getId())
                        .updatedBy(UserLoader.userDetails().getId())
                        .creationDate(now)
                        .updatedDate(now)
                        .isActive(true)
                        .isEnabled(true)
                        .build())
                .createdBy(UserLoader.userDetails().getId())
                .updatedBy(UserLoader.userDetails().getId())
                .creationDate(now)
                .updatedDate(now)
                .role(createDto.getRole())
                .isActive(true)
                .isEnabled(true)
                .build();
    }

    @Override
    public UserResponse mapEntityToResponseDto(WaraqUserEntity entity) {

        return UserResponse.builder()
                .id(entity.getId())
                .userId(entity.getUser().getId())
                .isEnabled(entity.getIsEnabled())
                .creationDate(entity.getCreationDate())
                .createdById(entity.getCreatedBy())
                .updateDate(entity.getUpdatedDate())
                .updatedById(entity.getUpdatedBy())
                .firstName(entity.getUser().getFirstName())
                .lastName(entity.getUser().getLastName())
                .email(entity.getUser().getEmail().toLowerCase())
                .userName(entity.getUser().getUsername())
                .role(entity.getRole())
                .build();
    }

    @Override
    public UserResponse mapEntityToListResponse(WaraqUserEntity entity) {
        return UserResponse.builder()
                .id(entity.getId())
                .userId(entity.getUser().getId())
                .firstName(entity.getUser().getFirstName())
                .lastName(entity.getUser().getLastName())
                .email(entity.getUser().getEmail().toLowerCase())
                .isEnabled(entity.getIsEnabled())
                .creationDate(entity.getCreationDate())
                .role(entity.getRole())
                .build();
    }
}
