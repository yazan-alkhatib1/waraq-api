package com.waraq.repositories;

import com.waraq.entities.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity> extends JpaRepository<T, Long> {

    Optional<T> findByIdAndIsActiveTrue(Long id);

    Optional<T> findByIdAndIsActiveFalse(Long id);

    Optional<T> findByIdAndIsEnabledTrue(Long id);

    Optional<T> findByIdAndIsEnabledFalse(Long id);

    Optional<T> findByIdAndIsEnabledTrueAndIsActiveTrue(Long id);

    Page<T> findAllByIsActiveTrue(Pageable pageable);

    Page<T> findAllByIsActiveTrueAndIsEnabledTrue(Pageable pageable);
}
