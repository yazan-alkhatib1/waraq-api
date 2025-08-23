package com.waraq.repository.repositories.user;

import com.waraq.repositories.BaseRepository;
import com.waraq.repository.entities.user.WaraqUserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WaraqUserRepository extends BaseRepository<WaraqUserEntity> {

    @Query("SELECT a from #{#entityName} a WHERE user_id = :userId AND is_active=true")
    Optional<WaraqUserEntity> findByUserId(Long userId);

    @Query("SELECT a from #{#entityName} a WHERE user_id = :userId AND is_active=true AND is_enabled=true")
    Optional<WaraqUserEntity> findActiveEnabledByUserId(Long userId);

    @Query("SELECT a FROM #{#entityName} a WHERE CONCAT( lower(a.user.firstName), ' ', lower(a.user.lastName), ' ', " +
            "lower(a.user.email), ' ', lower(a.user.phoneNumber)) LIKE %:keyword%  AND a.isActive=TRUE AND a.isEnabled= :isEnabled")
    Page<WaraqUserEntity> searchByIsEnabled(Pageable pageable, String keyword, Boolean isEnabled);

    @Query("SELECT a FROM #{#entityName} a WHERE CONCAT( lower(a.user.firstName), ' ', lower(a.user.lastName), ' ', lower(a.user.email), ' '," +
            " lower(a.user.phoneNumber)) LIKE %:keyword%  AND a.isActive=TRUE")
    Page<WaraqUserEntity> search(Pageable pageable, String keyword);

    Optional<WaraqUserEntity> findByUser_Email(String email);
}
