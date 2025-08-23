package com.waraq.repository.entities.user;
import com.waraq.entities.BaseEntity;
import com.waraq.repository.entities.media.MediaEntity;
import com.waraq.repository.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "waraq_users")
public class WaraqUserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "image_id")
    private MediaEntity image;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserEntity user;
    @Column(name = "role")
    private Role role;

    @Builder

    public WaraqUserEntity(LocalDateTime creationDate, LocalDateTime updatedDate, Boolean isActive,
                           Boolean isEnabled, Long createdBy,
                           Long updatedBy, Long id, MediaEntity image, UserEntity user, Role role) {
        super(creationDate, updatedDate, isActive, isEnabled, createdBy, updatedBy);
        this.id = id;
        this.image = image;
        this.user = user;
        this.role = role;
    }
}
