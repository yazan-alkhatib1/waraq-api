package com.waraq.service.mappers.admin;

import com.waraq.dto.admin.UserResponse;
import com.waraq.dto.admin.CreateUserRequest;
import com.waraq.helpers.UserLoader;
import com.waraq.repository.entities.user.AdminEntity;
import com.waraq.repository.entities.user.UserEntity;
import com.waraq.service.mappers.BaseMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

public class AdminMapper implements BaseMapper<AdminEntity, CreateUserRequest, UserResponse> {

    private final BCryptPasswordEncoder encoder;
    private final static String DEFAULT_LANGUAGE = "en";

    public AdminMapper(BCryptPasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public AdminEntity mapCreateDtoToEntity(CreateUserRequest createDto) {
        final LocalDateTime now = LocalDateTime.now();
        return AdminEntity.builder()
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
                .isActive(true)
                .isEnabled(true)
                .build();
    }

    @Override
    public UserResponse mapEntityToResponseDto(AdminEntity entity) {

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
                .build();
    }

    @Override
    public UserResponse mapEntityToListResponse(AdminEntity entity) {
        return UserResponse.builder()
                .id(entity.getId())
                .userId(entity.getUser().getId())
                .firstName(entity.getUser().getFirstName())
                .lastName(entity.getUser().getLastName())
                .email(entity.getUser().getEmail().toLowerCase())
                .isEnabled(entity.getIsEnabled())
                .creationDate(entity.getCreationDate())
                .build();
    }
}
