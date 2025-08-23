package com.waraq.dto.address.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.waraq.dto.ResponseDto;
import com.waraq.enums.CountryCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressResponse implements ResponseDto {

    private Long id;

    private String city;

    private String country;

    private CountryCode countryCode;

    private String houseNumber;

    private String street;

    private String postalCode;

    private String state;
}
