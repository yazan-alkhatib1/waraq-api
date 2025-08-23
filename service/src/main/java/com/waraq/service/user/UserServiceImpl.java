package com.waraq.service.user;

import com.padelgate.bo.user.VerifyEmailRequest;
import com.padelgate.bo.user.VerifyEmailResponse;
import com.padelgate.bo.user.VerifyPhoneNumberRequest;
import com.padelgate.bo.user.VerifyPhoneNumberResponse;
import com.padelgate.repository.entities.users.UserEntity;
import com.padelgate.repository.repositories.RepositoryFactory;
import com.padelgate.repository.repositories.users.UserRepository;
import com.padelgate.service.validators.CompositeValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static com.padelgate.service.validators.CompositeValidator.hasValue;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final RepositoryFactory repositoryFactory;

    public UserServiceImpl(RepositoryFactory repositoryFactory) {
        this.repositoryFactory = repositoryFactory;
    }

    @Override
    public VerifyPhoneNumberResponse verifyPhoneNumber(VerifyPhoneNumberRequest request) {
        log.info("Start Executing Verify Phone Number in user service for request : {}", request.getPhoneNumber());

        List<String> violations = new CompositeValidator<VerifyPhoneNumberRequest, String>()
                .addValidator(r -> hasValue(r.getPhoneNumber()), "Phone Number cannot be empty")
                .addValidator(r -> !hasValue(r.getPhoneNumber()) || r.getPhoneNumber().length() <= 15, "Phone Number is 15 characters max")
                .validate(request);

        CompositeValidator.joinViolations(violations);

        Optional<UserEntity> userEntity = repositoryFactory.getRepository(UserRepository.class)
                .findOne(Example.of(UserEntity.builder()
                        .isRemoved(false)
                                .isInvited(false)
                        .phoneNumber(request.getPhoneNumber())
                        .build()));

        if(userEntity.isPresent()){
            return VerifyPhoneNumberResponse.builder()
                    .exists(true)
                    .build();
        }
        return VerifyPhoneNumberResponse.builder()
                .exists(false)
                .build();
    }

    @Override
    public VerifyEmailResponse verifyEmail(VerifyEmailRequest request) {
        log.info("Start Executing Verify Email in user service for request : {}", request.getEmail());

        List<String> violations = new CompositeValidator<VerifyEmailRequest, String>()
                .addValidator(r -> hasValue(r.getEmail()), "Email cannot be empty")
                .addValidator(r -> !hasValue(r.getEmail()) || Pattern.compile("^(?!.*\\.{2})[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$").matcher(r.getEmail()).matches(), "Enter a valid email format")
                .validate(request);

        CompositeValidator.joinViolations(violations);

        Optional<UserEntity> userEntity = repositoryFactory.getRepository(UserRepository.class)
                .findOne(Example.of(UserEntity.builder()
                        .isRemoved(false)
                        .email(request.getEmail().toLowerCase())
                        .build()));

        if(userEntity.isPresent()){
            return VerifyEmailResponse.builder()
                    .exists(true)
                    .build();
        }
        return VerifyEmailResponse.builder()
                .exists(false)
                .build();
    }

}
