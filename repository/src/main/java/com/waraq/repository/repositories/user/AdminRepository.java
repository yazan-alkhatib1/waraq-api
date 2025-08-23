package com.waraq.repository.repositories.user;

import com.waraq.repositories.BaseRepository;
import com.waraq.repository.entities.user.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends BaseRepository<AdminEntity, Long> {
    @Query("SELECT a from #{#entityName} a WHERE user_id = :userId AND is_active=true")
    Optional<AdminEntity> findByUserId(Long userId);
}
