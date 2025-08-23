package com.waraq.service.helpers.validators;

import com.waraq.repository.entities.user.UserEntity;
import com.waraq.repository.repositories.user.UserRepository;
import org.springframework.data.domain.Example;

public class PhoneNumberValidator {

    private static final String PHONE_NUMBER_REGEX = "^\\d{1,15}$";

    public static Boolean validatePhoneNumberFormat(String phoneNumber) {
        return phoneNumber.matches(PHONE_NUMBER_REGEX);
    }

    public static Boolean validatePhoneNumberUniquenessOnCreation(String phoneNumber, UserRepository userRepository) {
        return userRepository.exists(Example.of(UserEntity.builder()
                .phoneNumber(phoneNumber)
                .build()));
    }
}
