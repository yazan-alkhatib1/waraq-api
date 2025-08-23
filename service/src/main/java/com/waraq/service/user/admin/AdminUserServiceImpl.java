package com.waraq.service.user.admin;

import com.waraq.dto.admin.AdminCreateUserRequest;
import com.waraq.dto.admin.AdminUserResponse;
import com.waraq.dto.admin.AdminUpdateUserRequest;
import com.waraq.dto.admin.UpdateEnabledPlayerRequest;
import com.waraq.exceptions.BodyGuardException;
import com.waraq.helpers.UserLoader;
import com.waraq.repositories.RepositoryFactory;
import com.waraq.repository.entities.media.MediaEntity;
import com.waraq.repository.entities.user.WaraqUserEntity;
import com.waraq.repository.repositories.media.MediaRepository;
import com.waraq.repository.repositories.user.WaraqUserRepository;
import com.waraq.service.base.BaseServiceImpl;
import com.waraq.service.mappers.user.AdminUserMapper;
import com.waraq.service.user.validation.UserValidationService;
import com.waraq.validator.CompositeValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.amazonaws.util.StringUtils.hasValue;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
@Service
public class AdminUserServiceImpl extends BaseServiceImpl<WaraqUserEntity, AdminCreateUserRequest, AdminUpdateUserRequest, AdminUserResponse> implements AdminUserService {

    private final WaraqUserRepository waraqUserRepository;
    private final AdminUserMapper adminUserMapper;
    private final UserValidationService userValidationService;
    private final RepositoryFactory repositoryFactory;
    public AdminUserServiceImpl(RepositoryFactory repositoryFactory, UserValidationService userValidationService) {
        this.waraqUserRepository = repositoryFactory.getRepository(WaraqUserRepository.class);
        this.adminUserMapper = new AdminUserMapper(repositoryFactory);
        this.userValidationService = userValidationService;
        this.repositoryFactory = repositoryFactory;
    }

    @Override
    public WaraqUserRepository getRepository() {
        return waraqUserRepository;
    }
    @Override
    public AdminUserMapper getMapper() {
        return adminUserMapper;
    }
    @Override
    public void updateEntityProps(WaraqUserEntity oldEntity, AdminUpdateUserRequest updateDto) {
        log.info("Start Executing update entity props in player service for ID : {}", oldEntity.getId());

        userValidationService.validateEmailOfPreUpdate(oldEntity.getUser(), updateDto.getEmail());
        final LocalDateTime now = LocalDateTime.now();

        oldEntity.getUser().setFirstName(updateDto.getFirstName());
        oldEntity.getUser().setLastName(updateDto.getLastName());
        oldEntity.getUser().setUsername(updateDto.getPhoneNumber());
        oldEntity.getUser().setEmail(updateDto.getEmail().toLowerCase());
        oldEntity.getUser().setPhoneNumber(updateDto.getPhoneNumber());
        oldEntity.getUser().setUpdatedBy(UserLoader.userDetails().getId());
        oldEntity.getUser().setUpdatedDate(now);
        oldEntity.setImage(getImage(updateDto));
        oldEntity.setUpdatedBy(UserLoader.userDetails().getId());
        oldEntity.setUpdatedDate(now);
        log.info("End Executing update entity props in player service for ID : {}", oldEntity.getId());
    }

    @Override
    public void preAddValidation(AdminCreateUserRequest createDto) {
        List<String> violations = new CompositeValidator<AdminCreateUserRequest, String>()
                .addValidator(r -> hasValue(r.getFirstName()), "First Name Cannot Be Empty")
                .addValidator(r -> hasValue(r.getLastName()), "Last Name Cannot Be Empty")
                .addValidator(r -> isNull(r.getImageId()) || repositoryFactory.getRepository(MediaRepository.class).existsById(r.getImageId()),
                        "No Image found with this id {" + createDto.getImageId() + "}")
               .validate(createDto);
        validate(violations);
        userValidationService.validateEmailOfCreation(createDto.getEmail());
    }

    @Override
    public void updatePreValidation(AdminUpdateUserRequest updateDTO) {
        List<String> violations = new CompositeValidator<AdminUpdateUserRequest, String>()
                .addValidator(r -> hasValue(r.getFirstName()), "First Name Cannot Be Empty")
                .addValidator(r -> hasValue(r.getLastName()), "Last Name Cannot Be Empty")
                .addValidator(r -> isNull(r.getImageId())
                                || repositoryFactory.getRepository(MediaRepository.class).findById(r.getImageId()).isPresent(),
                        "No Image found with this id {" + updateDTO.getImageId() + "}")
                .validate(updateDTO);
        validate(violations);
    }

 

    @Override
    public void updateEnabledStatus(UpdateEnabledPlayerRequest updateDto) {
        log.info("Start Executing update enabled status in player service for ID : {}", updateDto.getId());

        List<String> violations = new CompositeValidator<UpdateEnabledPlayerRequest, String>()
                .addValidator(r -> nonNull(r.getId()), "id Cannot Be Empty")
                .addValidator(r -> nonNull(r.getIsEnabled()), "is Enabled Cannot Be Empty")
                .validate(updateDto);
        validate(violations);

        WaraqUserEntity entity = super.findActiveEntityById(updateDto.getId());

        LocalDateTime now = LocalDateTime.now();
        entity.getUser().setUpdatedDate(now);
        entity.getUser().setUpdatedBy(UserLoader.userDetails().getId());
        entity.getUser().setIsEnabled(updateDto.getIsEnabled());

        entity.setUpdatedDate(now);
        entity.setUpdatedBy(UserLoader.userDetails().getId());
        entity.setIsEnabled(updateDto.getIsEnabled());

        getRepository().save(entity);
        log.info("End Executing update enabled status in player service for ID : {}", updateDto.getId());

    }

    private MediaEntity getImage(AdminUpdateUserRequest updateDto) {
        return nonNull(updateDto.getImageId())
                ? repositoryFactory.getRepository(MediaRepository.class).findById(updateDto.getImageId())
                .orElseThrow(() -> new BodyGuardException("Cannot find active image by id (" + updateDto.getImageId() + ")"))
                : null;
    }
}
