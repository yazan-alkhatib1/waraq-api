package com.waraq.service.helpers.validators;

import com.waraq.repository.entities.user.UserEntity;
import com.waraq.repository.repositories.user.UserRepository;
import org.springframework.data.domain.Example;

public class EmailValidator {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    public static Boolean validateEmailFormat(String email) {
        return email.matches(EMAIL_REGEX);
    }

    public static Boolean validateEmailUniquenessOnCreation(String email, UserRepository userRepository) {
        return userRepository.exists(Example.of(UserEntity.builder()
                .email(email)
                .build()));
    }
}
