package com.waraq.repository.entities.translate;

import com.waraq.entities.BaseEntity;
import com.waraq.enums.CountryCode;
import com.waraq.repository.entities.media.MediaEntity;
import com.waraq.repository.enums.TranslateRequestStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "documents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TranslateDocEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "status")
    private TranslateRequestStatus status;

    @OneToOne
    @JoinColumn(name = "request_id")
    private TranslateRequestEntity request;

    @OneToOne
    @JoinColumn(name = "document_id")
    private MediaEntity document;

    @OneToOne
    @JoinColumn(name = "template_Id")
    private MediaEntity template;

    @OneToOne
    @JoinColumn(name = "translated_document_id")
    private MediaEntity translatedDocument;

    @OneToOne
    @JoinColumn(name = "final_translated_document_id")
    private MediaEntity finalTranslatedDocument;

@Builder

    public TranslateDocEntity(LocalDateTime creationDate, LocalDateTime updatedDate, Boolean isActive,
                              Boolean isEnabled, Long createdBy, Long updatedBy, Long id, TranslateRequestStatus status,
                              MediaEntity document, MediaEntity template,
                              MediaEntity translatedDocument, MediaEntity finalTranslatedDocument) {
        super(creationDate, updatedDate, isActive, isEnabled, createdBy, updatedBy);
        this.id = id;
        this.status = status;
        this.document = document;
        this.template = template;
        this.translatedDocument = translatedDocument;
        this.finalTranslatedDocument = finalTranslatedDocument;
    }
}
