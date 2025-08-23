package com.waraq.repository.entities.address;

import com.waraq.entities.BaseEntity;
import com.waraq.enums.CountryCode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "addresses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "country_code")
    @Enumerated(EnumType.STRING)
    private CountryCode countryCode;

    @Column(name = "house_number")
    private String houseNumber;

    @Column(name = "street")
    private String street;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "state")
    private String state;

    @Builder
    public AddressEntity(LocalDateTime creationDate, LocalDateTime updatedDate,
                         Boolean isActive, Boolean isEnabled, Long createdBy,
                         Long updatedBy, Long id, String city, String country,
                         String houseNumber, String street, String postalCode, String state, CountryCode countryCode) {
        super(creationDate, updatedDate, isActive, isEnabled, createdBy, updatedBy);
        this.id = id;
        this.city = city;
        this.country = country;
        this.houseNumber = houseNumber;
        this.street = street;
        this.postalCode = postalCode;
        this.state = state;
        this.countryCode = countryCode;
    }
}
