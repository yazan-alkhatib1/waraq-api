package com.waraq.dto.user.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.waraq.dto.ResponseDto;
import com.waraq.dto.address.response.AddressResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserSignupResponse implements ResponseDto {

    private Long id;

    private Boolean isEnabled;

    private LocalDateTime creationDate;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String jwtToken;

    private AddressResponse address;
}
