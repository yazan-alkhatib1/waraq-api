package com.waraq.repository.repositories.user;

import com.waraq.repository.entities.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("SELECT u FROM #{#entityName} u WHERE is_active = TRUE AND user_name =  :username")
    Optional<UserEntity> findByUserName(String username);

    @Query("SELECT u FROM #{#entityName} u WHERE is_active = TRUE AND email =  :email")
    Optional<UserEntity> findByEmailActive(String email);

    @Query("SELECT u FROM #{#entityName} u WHERE is_active = TRUE AND phone_number =  :phone")
    Optional<UserEntity> findByPhoneNumber(String phone);
}
