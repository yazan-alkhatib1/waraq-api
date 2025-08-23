package com.waraq.repository.entities.translate;

import com.waraq.entities.BaseEntity;

import com.waraq.repository.entities.media.MediaEntity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "templates")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TranslateTemplateEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "document_id")
    private MediaEntity document;

    @Builder

    public TranslateTemplateEntity(LocalDateTime creationDate, LocalDateTime updatedDate, Boolean isActive,
                                   Boolean isEnabled, Long createdBy,
                                   Long updatedBy, Long id, MediaEntity document) {
        super(creationDate, updatedDate, isActive, isEnabled, createdBy, updatedBy);
        this.id = id;
        this.document = document;
    }
}
