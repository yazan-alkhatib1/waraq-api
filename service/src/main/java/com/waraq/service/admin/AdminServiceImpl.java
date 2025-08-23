package com.waraq.service.admin;

import com.waraq.dto.admin.*;
import com.waraq.exceptions.BodyGuardException;
import com.waraq.exceptions.NotFoundException;
import com.waraq.helpers.UserLoader;
import com.waraq.repositories.RepositoryFactory;
import com.waraq.repository.entities.user.AdminEntity;
import com.waraq.repository.repositories.user.AdminRepository;
import com.waraq.service.base.BaseServiceImpl;
import com.waraq.service.mappers.admin.AdminMapper;
import com.waraq.service.user.validation.UserValidationService;
import com.waraq.validator.CompositeValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import static com.waraq.validator.CompositeValidator.hasValue;
import static java.time.LocalDateTime.now;
import static java.util.Objects.nonNull;

@Service
@Slf4j
public class AdminServiceImpl extends BaseServiceImpl<AdminEntity, CreateUserRequest, UpdateUserRequest, UserResponse> implements AdminService {

    private final RepositoryFactory repositoryFactory;
    private final BCryptPasswordEncoder encoder;
    private final AdminRepository adminRepository;
    private final AdminMapper adminMapper;
    private final UserValidationService userValidationService;

    public AdminServiceImpl(RepositoryFactory repositoryFactory, BCryptPasswordEncoder encoder, UserValidationService userValidationService) {
        this.repositoryFactory = repositoryFactory;
        this.encoder = encoder;
        this.adminRepository = repositoryFactory.getRepository(AdminRepository.class);
        this.adminMapper = new AdminMapper(encoder);
        this.userValidationService = userValidationService;
    }

    @Override
    public AdminRepository getRepository() {
        return adminRepository;
    }

    @Override
    public AdminMapper getMapper() {
        return adminMapper;
    }

    @Override
    public void updateEnabled(UpdateEnabledAdminRequest request) {
        log.info("Start execution of Enable/Disable Admin for ID: {} With status : {}", request.getId(), request.getIsEnabled());

        List<String> violations = new CompositeValidator<UpdateEnabledAdminRequest, String>().addValidator(r -> nonNull(r.getId()), "Admin Id Cannot Be Empty").addValidator(r -> nonNull(r.getIsEnabled()), "Is-Enabled Field Cannot Be Empty").validate(request);
        validate(violations);

        AdminEntity entity = super.findActiveEntityById(request.getId());

        LocalDateTime now = now();
        entity.setIsEnabled(request.getIsEnabled());
        entity.getUser().setIsEnabled(request.getIsEnabled());
        entity.getUser().setUpdatedDate(now);
        entity.getUser().setUpdatedBy(UserLoader.userDetails().getId());
        entity.setUpdatedDate(now);
        entity.setUpdatedBy(UserLoader.userDetails().getId());

        repositoryFactory.getRepository(AdminRepository.class).save(entity);

        log.info("End execution of Enable/Disable Admin for ID: {} With status : {}", request.getId(), request.getIsEnabled());
    }

    @Override
    public void updatePassword(UpdatePasswordRequest request) {
        log.info("Start execution of Change Password of Admin for ID: {}", request.getId());

        preUpdatePasswordValidation(request);
        AdminEntity entity = getRepository().findByUserId(request.getId()).orElseThrow(() -> new BodyGuardException("Cannot find active admin by id (" + request.getId() + ")"));
        entity.getUser().setPassword(encoder.encode(request.getNewPassword()));
        getRepository().save(entity);
        log.info("End execution of Change Password of Admin for ID: {}", request.getId());
    }

    @Override
    public UserResponse findAdminByUserId(Long userId) {
        log.info("Start execution of Find Admin By User ID : {}", userId);

        return getMapper().mapEntityToResponseDto(getRepository().findByUserId(userId).orElseThrow(() -> new NotFoundException("No Admin with User Id {" + userId + "} Was Found")));
    }

    @Override
    public void updateEntityProps(AdminEntity oldEntity, UpdateUserRequest updateDto) {
        log.info("Start execution of UpdateEntityProps Method in Admin Service");

        final LocalDateTime now = now();

        oldEntity.getUser().setFirstName(updateDto.getFirstName());
        oldEntity.getUser().setLastName(updateDto.getLastName());
        oldEntity.getUser().setUsername(updateDto.getEmail().toLowerCase());
        oldEntity.getUser().setEmail(updateDto.getEmail().toLowerCase());
        oldEntity.getUser().setPhoneNumber(updateDto.getPhoneNumber());
        oldEntity.setUpdatedBy(UserLoader.userDetails().getId());
        oldEntity.setUpdatedDate(now);

        log.info("End execution of UpdateEntityProps Method in Admin Service");
    }

    @Override
    public void preAddValidation(CreateUserRequest createDto) {
        log.info("Start execution of PreAddValidation Method in Admin Service");

        List<String> violations = new CompositeValidator<CreateUserRequest, String>()
                .addValidator(r -> hasValue(r.getFirstName()), "First Name Cannot Be Empty")
                .addValidator(r -> hasValue(r.getLastName()), "Last Name Cannot Be Empty")
                .addValidator(r -> hasValue(r.getPassword()), "Password Cannot Be Empty")
                .addValidator(r -> !hasValue(r.getPassword()) || Pattern.compile("^(?=.*[A-Z]).{8,20}$").matcher(r.getPassword()).matches(), "Password should be minimum 8 and maximum 20 characters with 1 upper case letter")
                .validate(createDto);
        validate(violations);
        userValidationService.validateEmailOfCreation(createDto.getEmail());
        log.info("End execution of PreAddValidation Method in Admin Service");
    }

    @Override
    public void updatePreValidation(UpdateUserRequest updateDTO) {
        log.info("Start execution of updatePreValidation Method in Admin Service");

        List<String> violations = new CompositeValidator<UpdateUserRequest, String>()
                .addValidator(r -> hasValue(r.getFirstName()), "First Name Cannot Be Empty")
                .addValidator(r -> hasValue(r.getLastName()), "Last Name Cannot Be Empty")
                .addValidator(r -> hasValue(r.getEmail()), "Email Cannot Be Empty")
                .validate(updateDTO);
        validate(violations);

        log.info("End execution of updatePreValidation Method in Admin Service");
    }

    @Override
    public void preUpdate(UpdateUserRequest updateDto, Long id) {
        log.info("Start execution of preUpdate Method in Admin Service");

        AdminEntity entity = super.findActiveEntityById(id);

        if (nonNull(updateDto.getEmail()))
            userValidationService.validateEmailOfPreUpdate(entity.getUser(), updateDto.getEmail());


        log.info("End execution of preUpdate Method in Admin Service");
    }


    private void preUpdatePasswordValidation(UpdatePasswordRequest request) {
        log.info("Start execution of Password Validation Method in Admin Service");

        List<String> violations = new CompositeValidator<UpdatePasswordRequest, String>().addValidator(r -> nonNull(r.getId()), "Admin Id field cannot be empty").addValidator(r -> hasValue(r.getNewPassword()), "New password field cannot be empty").addValidator(r -> hasValue(r.getConfirmPassword()), "Confirm password field cannot be empty").addValidator(r -> (!hasValue(r.getNewPassword()) && !hasValue(r.getConfirmPassword())) || Objects.equals(request.getNewPassword(), request.getConfirmPassword()), "Password does not match").addValidator(r -> Pattern.compile("^(?=.*[A-Z]).{8,20}$").matcher(r.getNewPassword()).matches(), "Password should be minimum 8 and maximum 20 characters with 1 upper case letter").validate(request);
        validate(violations);

        log.info("End execution of Password Validation Method in Admin Service");
    }

}
