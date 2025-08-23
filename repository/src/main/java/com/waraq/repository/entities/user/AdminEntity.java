package com.waraq.repository.entities.user;


import com.waraq.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "admins")
public class AdminEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private UserEntity user;


    @Builder(toBuilder = true)
    public AdminEntity(LocalDateTime creationDate, LocalDateTime updatedDate, Boolean isActive,
                       Boolean isEnabled, Long createdBy, Long updatedBy, Long id, UserEntity user) {
        super(creationDate, updatedDate, isActive, isEnabled, createdBy, updatedBy);
        this.id = id;
        this.user = user;
    }
}
