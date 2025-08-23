package com.waraq.service.user.validation;

import com.waraq.repository.entities.user.UserEntity;
import com.waraq.repository.repositories.user.UserRepository;
import com.waraq.validator.CompositeValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

import static com.waraq.validator.CompositeValidator.hasValue;
import static com.waraq.validator.CompositeValidator.joinViolations;
import static java.util.Objects.isNull;

@Service
@Slf4j
public class UserValidationServiceImpl implements UserValidationService{

    private final UserRepository userRepository;
    public UserValidationServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validateEmailOfCreation(String email) {
        log.info("Start execution of validate Email of creation for email : {}", email);

        List<String> violations = new CompositeValidator<String, String>()
                .addValidator(CompositeValidator::hasValue, "Email Cannot Be Empty")
                .addValidator(r -> isNull(r) || !userRepository.findByEmailActive(r.toLowerCase()).isPresent(), "Email already exists")
                .addValidator(r -> isNull(r) || Pattern.compile("^(?!.*\\.{2})[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$").matcher(r).matches(), "Enter a valid email")
                .validate(email);
        joinViolations(violations);

        log.info("End execution of validate Email of creation for email : {}", email);
    }

    @Override
    public void validateEmailOfPreUpdate(UserEntity user, String email) {
        log.info("Start execution of validate Email of update for email : {}", email);

        if (!(user.getEmail().equalsIgnoreCase(email))) {
            List<String> violations = new CompositeValidator<String, String>()
                    .addValidator(CompositeValidator::hasValue, "Email Cannot Be Empty")
                    .addValidator(r -> isNull(r) || Pattern.compile("^(?!.*\\.{2})[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$").matcher(r).matches(), "Enter a valid email")
                    .addValidator(r -> isNull(r) || !userRepository.findByEmailActive(r).isPresent(), "Email already exists")
                    .validate(email);
            joinViolations(violations);
        }

        log.info("End execution of validate Email of update for email : {}", email);
    }

    public void validatePhoneNumberOfCreation(String phoneNumber) {
        List<String> violations = new CompositeValidator<String, String>()
                .addValidator(CompositeValidator::hasValue, "Phone Number Cannot be empty")
                .addValidator(r -> isNull(r) || !userRepository.findByPhoneNumber(r).isPresent(), "Phone number already exist.")
                .addValidator(r -> isNull(r) || r.length() <= 15, "Phone Number should be maximum 15 characters")
                .validate(phoneNumber);
        joinViolations(violations);
    }


}
