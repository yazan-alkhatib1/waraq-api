package com.waraq.dto.auth.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.waraq.dto.ResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponse implements ResponseDTO {

    private Long id;

    private Boolean isEnabled;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String jwtToken;
}
