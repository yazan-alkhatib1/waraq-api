package com.waraq.dto.address.request;

import com.waraq.dto.RequestDto;
import com.waraq.enums.CountryCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateAddressRequest implements RequestDto {

    private String city;

    private CountryCode countryCode;

    private String houseNumber;

    private String street;

    private String postalCode;

    private String state;
}
