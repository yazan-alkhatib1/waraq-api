package com.waraq.entities;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class BaseEntity {

    @CreationTimestamp
    @Column(name = "creation_date")
    protected LocalDateTime creationDate;

    @UpdateTimestamp
    @Column(name = "updated_date")
    protected LocalDateTime updatedDate;

    @Column(name = "is_active")
    protected Boolean isActive = true;

    @Column(name = "is_enabled", nullable = false)
    protected Boolean isEnabled;

    @Column(name = "created_by")
    protected Long createdBy;

    @Column(name = "updated_by")
    protected Long updatedBy;

    public BaseEntity(LocalDateTime creationDate, LocalDateTime updatedDate, Boolean isActive, Boolean isEnabled,
                      Long createdBy, Long updatedBy) {
        this.creationDate = creationDate;
        this.updatedDate = updatedDate;
        this.isActive = isActive;
        this.isEnabled = isEnabled;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }
}
