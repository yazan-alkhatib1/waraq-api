package com.waraq.service.admin;

import com.waraq.dto.admin.UserResponse;
import com.waraq.dto.admin.profile.UpdateProfileRequest;
import com.waraq.dto.admin.profile.UpdateProfilePasswordRequest;
import com.waraq.exceptions.NotFoundException;
import com.waraq.helpers.UserLoader;
import com.waraq.repositories.RepositoryFactory;
import com.waraq.repository.entities.user.AdminEntity;
import com.waraq.repository.repositories.user.AdminRepository;
import com.waraq.service.mappers.admin.AdminMapper;
import com.waraq.validator.CompositeValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

import static java.util.Objects.isNull;

@Slf4j
@Service
public class AdminProfileManagementServiceImpl implements AdminProfileManagementService {

    private final BCryptPasswordEncoder encoder;
    private final AdminMapper adminMapper;
    private final AdminRepository adminRepository;
    public AdminProfileManagementServiceImpl(BCryptPasswordEncoder encoder,
                                             RepositoryFactory repositoryFactory) {
        this.encoder = encoder;
        this.adminMapper = new AdminMapper(encoder);
        this.adminRepository = repositoryFactory.getRepository(AdminRepository.class);
    }

    public AdminRepository getRepository() {
        return adminRepository;
    }

    public AdminMapper getMapper() {
        return adminMapper;
    }

    @Override
    public UserResponse retrieveProfile() {
        Long userId = UserLoader.userDetails().getId();
        return getMapper().mapEntityToResponseDto(getRepository().findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("Cannot find admin by user id (" + userId + ")")));
    }

    @Override
    public void updateOldPassword(UpdateProfilePasswordRequest request) {
        Long userId = UserLoader.userDetails().getId();
        AdminEntity admin = getRepository()
                .findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("Cannot find admin by user id (" + userId + ")"));
        preUpdateOldPasswordValidation(request, admin.getUser().getPassword());
        admin.getUser().setPassword(encoder.encode(request.getNewPassword()));
        getRepository().save(admin);
    }

    @Override
    public UserResponse updateAdminProfile(UpdateProfileRequest request) {
        Long userId = UserLoader.userDetails().getId();
        AdminEntity entity = getRepository()
                .findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("Cannot find admin by user id (" + userId + ")"));
        preUpdateAdminProfileValidation(request);

        entity.getUser().setFirstName(request.getFirstName());
        entity.getUser().setLastName(request.getLastName());
        entity = getRepository().save(entity);
        return getMapper().mapEntityToResponseDto(entity);

    }

    private void preUpdateAdminProfileValidation(UpdateProfileRequest request) {
        List<String> violations = new CompositeValidator<UpdateProfileRequest, String>()
                .addValidator(r -> CompositeValidator.hasValue(r.getFirstName()), "First Name cannot be empty")
                .addValidator(r -> CompositeValidator.hasValue(r.getLastName()), "Last Name cannot be empty")
                .validate(request);
        CompositeValidator.joinViolations(violations);
    }

    private void preUpdateOldPasswordValidation(UpdateProfilePasswordRequest request, String oldPassword) {
        List<String> violations = new CompositeValidator<UpdateProfilePasswordRequest, String>()
                .addValidator(r -> CompositeValidator.hasValue(r.getOldPassword()), "old password cannot be empty")
                .addValidator(r -> CompositeValidator.hasValue(r.getNewPassword()), "new password cannot be empty")
                .addValidator(r -> CompositeValidator.hasValue(r.getConfirmPassword()), "confirm password field cannot be empty")
                .addValidator(r -> (isNull(r.getOldPassword())) ||
                        (encoder.matches(r.getOldPassword(), oldPassword)), "Old password Doesn't match")
                .addValidator(r -> (isNull(r.getNewPassword())
                        || isNull(r.getConfirmPassword()))
                        || r.getNewPassword().equals(r.getConfirmPassword()), "Password does not match")
                .addValidator(r -> isNull(r.getNewPassword())
                        || Pattern.compile("^(?=.*[A-Z]).{8,20}$").matcher(r.getNewPassword()).matches(),
                        "Password should be minimum 8 characters with 1 upper case letter")
                .validate(request);
        CompositeValidator.joinViolations(violations);
    }

}
