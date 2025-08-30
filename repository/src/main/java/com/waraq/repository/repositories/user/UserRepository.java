package com.waraq.repository.repositories.user;

import com.waraq.repository.entities.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("SELECT u FROM #{#entityName} u WHERE u.isActive = TRUE AND u.username = :username")
    Optional<UserEntity> findByUserName(@Param("username") String username);

    @Query("SELECT u FROM #{#entityName} u WHERE u.isActive = TRUE AND u.email = :email")
    Optional<UserEntity> findByEmailActive(@Param("email") String email);

    @Query("SELECT u FROM #{#entityName} u WHERE u.isActive = TRUE AND u.phoneNumber = :phone")
    Optional<UserEntity> findByPhoneNumber(@Param("phone") String phone);
}
