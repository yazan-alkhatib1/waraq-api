package com.waraq.repository.repositories.user;

import com.waraq.repositories.BaseRepository;
import com.waraq.repository.entities.user.WaraqUserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WaraqUserRepository extends BaseRepository<WaraqUserEntity> {

    @Query("SELECT a FROM #{#entityName} a WHERE a.user.id = :userId AND a.isActive = true")
    Optional<WaraqUserEntity> findByUserId(@Param("userId") Long userId);

    @Query("SELECT a FROM #{#entityName} a WHERE a.user.id = :userId AND a.isActive = true AND a.isEnabled = true")
    Optional<WaraqUserEntity> findActiveEnabledByUserId(@Param("userId") Long userId);

    @Query("SELECT a FROM #{#entityName} a WHERE CONCAT(lower(a.user.firstName), ' ', lower(a.user.lastName), ' ', lower(a.user.email), ' ', lower(a.user.phoneNumber)) LIKE CONCAT('%', LOWER(:keyword), '%') AND a.isActive = TRUE AND a.isEnabled = :isEnabled")
    Page<WaraqUserEntity> searchByIsEnabled(Pageable pageable, @Param("keyword") String keyword, @Param("isEnabled") Boolean isEnabled);

    @Query("SELECT a FROM #{#entityName} a WHERE CONCAT(lower(a.user.firstName), ' ', lower(a.user.lastName), ' ', lower(a.user.email), ' ', lower(a.user.phoneNumber)) LIKE CONCAT('%', LOWER(:keyword), '%') AND a.isActive = TRUE")
    Page<WaraqUserEntity> search(Pageable pageable, @Param("keyword") String keyword);

    Optional<WaraqUserEntity> findByUser_Email(String email);
}
