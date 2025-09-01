package com.waraq.repository.entities.translate;

import com.waraq.entities.BaseEntity;
import com.waraq.enums.CountryCode;
import com.waraq.repository.entities.media.MediaEntity;
import com.waraq.repository.entities.user.UserEntity;
import com.waraq.repository.entities.user.WaraqUserEntity;
import com.waraq.repository.enums.TranslateRequestStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TranslateRequestEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "client_id")
    private WaraqUserEntity client;

    @Column(name = "request_date")
    private LocalDateTime requestDate;

    @Column(name = "student_name")
    private String studentName;

    @Column(name = "total_documents")
    private int totalPages;

    @OneToOne
    @JoinColumn(name = "manager_id")
    private WaraqUserEntity manager;

    @OneToOne
    @JoinColumn(name = "translator_id")
    private WaraqUserEntity translator;

    @OneToOne
    @JoinColumn(name = "proofreader_id")
    private WaraqUserEntity proofreader;

    @Column(name = "status")
    private TranslateRequestStatus status;

    @Column(name = "delivery_date")
    private LocalDateTime deliveryDate;

    @OneToOne
    @JoinColumn(name = "mistakes_id")
    private MediaEntity mistakesDocument;
@Builder

    public TranslateRequestEntity(LocalDateTime creationDate, LocalDateTime updatedDate,
                                  Boolean isActive, Boolean isEnabled, Long createdBy, Long updatedBy,
                                  Long id, WaraqUserEntity client, LocalDateTime requestDate,
                                  String studentName, int totalPages, WaraqUserEntity manager,
                                  WaraqUserEntity translator, WaraqUserEntity proofreader,
                                  TranslateRequestStatus status,
                                  LocalDateTime deliveryDate, MediaEntity mistakesDocument) {
        super(creationDate, updatedDate, isActive, isEnabled, createdBy, updatedBy);
        this.id = id;
        this.client = client;
        this.requestDate = requestDate;
        this.studentName = studentName;
        this.totalPages = totalPages;
        this.manager = manager;
        this.translator = translator;
        this.proofreader = proofreader;
        this.status = status;
        this.deliveryDate = deliveryDate;
        this.mistakesDocument = mistakesDocument;
    }
}
