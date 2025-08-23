package com.waraq.service.user.profile;

import com.waraq.dto.admin.UserResponse;
import com.waraq.dto.admin.profile.UpdateProfileRequest;
import com.waraq.dto.admin.profile.UpdateProfilePasswordRequest;
import com.waraq.exceptions.NotFoundException;
import com.waraq.helpers.UserLoader;
import com.waraq.repositories.RepositoryFactory;
import com.waraq.repository.entities.user.WaraqUserEntity;
import com.waraq.repository.repositories.user.WaraqUserRepository;
import com.waraq.service.mappers.user.UserMapper;
import com.waraq.validator.CompositeValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

import static java.util.Objects.isNull;

@Slf4j
@Service
public class UserProfileManagementServiceImpl implements UserProfileManagementService {

    private final BCryptPasswordEncoder encoder;
    private final UserMapper userMapper;
    private final WaraqUserRepository waraqUserRepository;
    public UserProfileManagementServiceImpl(BCryptPasswordEncoder encoder,
                                            RepositoryFactory repositoryFactory) {
        this.encoder = encoder;
        this.userMapper = new UserMapper(encoder);
        this.waraqUserRepository = repositoryFactory.getRepository(WaraqUserRepository.class);
    }

    public WaraqUserRepository getRepository() {
        return waraqUserRepository;
    }

    public UserMapper getMapper() {
        return userMapper;
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
        WaraqUserEntity user = getRepository()
                .findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("Cannot find user by user id (" + userId + ")"));
        preUpdateOldPasswordValidation(request, user.getUser().getPassword());
        user.getUser().setPassword(encoder.encode(request.getNewPassword()));
        getRepository().save(user);
    }

    @Override
    public UserResponse updateProfile(UpdateProfileRequest request) {
        Long userId = UserLoader.userDetails().getId();
        WaraqUserEntity entity = getRepository()
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
