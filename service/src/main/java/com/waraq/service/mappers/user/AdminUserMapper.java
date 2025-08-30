package com.waraq.service.mappers.user;



import com.waraq.dto.admin.AdminCreateUserRequest;
import com.waraq.dto.admin.AdminUserResponse;
import com.waraq.exceptions.BodyGuardException;
import com.waraq.helpers.UserLoader;
import com.waraq.repositories.RepositoryFactory;
import com.waraq.repository.entities.media.MediaEntity;
import com.waraq.repository.entities.user.UserEntity;
import com.waraq.repository.entities.user.WaraqUserEntity;
import com.waraq.repository.repositories.media.MediaRepository;
import com.waraq.service.mappers.BaseMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import static java.util.Objects.nonNull;


public class AdminUserMapper implements BaseMapper<WaraqUserEntity, AdminCreateUserRequest, AdminUserResponse> {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RepositoryFactory repositoryFactory;
    public AdminUserMapper(RepositoryFactory repositoryFactory, BCryptPasswordEncoder bCryptPasswordEncoder1) {
        this.repositoryFactory = repositoryFactory;

        this.bCryptPasswordEncoder = bCryptPasswordEncoder1;
    }
    @Override
    public WaraqUserEntity mapCreateDtoToEntity(AdminCreateUserRequest createDto) {
        final LocalDateTime now = LocalDateTime.now();
        return WaraqUserEntity.builder()
                .user(UserEntity.builder()
                        .firstName(createDto.getFirstName())
                        .lastName(createDto.getLastName())
                        .username(createDto.getPhoneNumber())
                        .email(createDto.getEmail().toLowerCase())
                        .phoneNumber(createDto.getPhoneNumber())
                        .password(bCryptPasswordEncoder.encode("waraq123"))
                        .createdBy(UserLoader.userDetails().getId())
                        .updatedBy(UserLoader.userDetails().getId())
                        .creationDate(now)
                        .updatedDate(now)
                        .isActive(true)
                        .isEnabled(true)
                        .build())
                .image(getImage(createDto))
                .role(createDto.getRole())
                .createdBy(UserLoader.userDetails().getId())
                .updatedBy(UserLoader.userDetails().getId())
                .creationDate(now)
                .updatedDate(now)
                .isActive(true)
                .isEnabled(true)
                .build();
    }

    @Override
    public AdminUserResponse mapEntityToResponseDto(WaraqUserEntity entity) {
        return AdminUserResponse.builder()
                .id(entity.getId())
                .isEnabled(entity.getIsEnabled())
                .creationDate(entity.getCreationDate())
                .imageId(getImageId(entity))
                .imageUrl(getImageUrl(entity))
                .firstName(entity.getUser().getFirstName())
                .lastName(entity.getUser().getLastName())
                .phoneNumber(entity.getUser().getPhoneNumber())
                .email(entity.getUser().getEmail())
                .role(entity.getRole())
                .build();
    }

    @Override
    public AdminUserResponse mapEntityToListResponse(WaraqUserEntity entity) {
        return AdminUserResponse.builder()
                .id(entity.getId())
                .isEnabled(entity.getIsEnabled())
                .creationDate(entity.getCreationDate())
                .imageId(getImageId(entity))
                .imageUrl(getImageUrl(entity))
                .firstName(entity.getUser().getFirstName())
                .lastName(entity.getUser().getLastName())
                .phoneNumber(entity.getUser().getPhoneNumber())
                .email(entity.getUser().getEmail())
                .role(entity.getRole())
                .build();
    }



    private MediaEntity getImage(AdminCreateUserRequest createDto) {
        return nonNull(createDto.getImageId())
                ? repositoryFactory.getRepository(MediaRepository.class).findById(createDto.getImageId())
                .orElseThrow(() -> new BodyGuardException("Cannot find active image by id (" + createDto.getImageId() + ")"))
                : null;
    }

    private Long getImageId(WaraqUserEntity entity) {
        return nonNull(entity.getImage())
                ? entity.getImage().getId()
                : null;
    }

    private String getImageUrl(WaraqUserEntity entity) {
        return nonNull(entity.getImage())
                ? entity.getImage().getMediaUrl()
                : null;
    }

}
