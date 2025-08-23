package com.waraq.service.user.validation;


import com.waraq.repository.entities.user.UserEntity;

public interface UserValidationService {

    void validateEmailOfCreation(String email);

    void validateEmailOfPreUpdate(UserEntity user, String email);

}
