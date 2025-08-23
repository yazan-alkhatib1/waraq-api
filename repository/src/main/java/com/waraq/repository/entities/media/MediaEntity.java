package com.waraq.repository.entities.media;

import com.waraq.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "media")
public class MediaEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "media_url")
    private String mediaUrl;

    @Column(name = "type")
    private String type;

    @Column(name = "mime_type")
    private String mimeType;

    @Builder
    public MediaEntity(LocalDateTime creationDate, LocalDateTime updatedDate,
                       Boolean isActive,
                       Long createdBy,
                       Long updatedBy,
                       Boolean isEnabled,
                       Long id,
                       String mediaUrl,
                       String type,
                       String mimeType) {
        super(creationDate, updatedDate, isActive, isEnabled, createdBy, updatedBy);
        this.id = id;
        this.mediaUrl = mediaUrl;
        this.type = type;
        this.mimeType = mimeType;
    }

}
