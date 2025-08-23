package com.waraq.repository.entities.user;

import com.waraq.entities.BaseEntity;
import com.waraq.repository.entities.address.AddressEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "user_name", unique = true, nullable = false)
    private String username;
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToOne
    @JoinColumn(name = "address_id")
    private AddressEntity address;

    @Builder(toBuilder = true)

    public UserEntity(LocalDateTime creationDate, LocalDateTime updatedDate, Boolean isActive, Boolean isEnabled,
                      Long createdBy, Long updatedBy, Long id, String username, String firstName, String lastName,
                      String email, String phoneNumber, String password, AddressEntity address) {
        super(creationDate, updatedDate, isActive, isEnabled, createdBy, updatedBy);
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.address = address;
    }
}
