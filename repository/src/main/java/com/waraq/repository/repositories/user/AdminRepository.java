package com.waraq.repository.repositories.user;

import com.waraq.repositories.BaseRepository;
import com.waraq.repository.entities.user.AdminEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends BaseRepository<AdminEntity> {
    @Query("SELECT a FROM #{#entityName} a WHERE a.user.id = :userId AND a.isActive = true")
    Optional<AdminEntity> findByUserId(@Param("userId") Long userId);
}
