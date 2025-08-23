package com.waraq.dto.user.request;

import com.waraq.dto.RequestDto;
import com.waraq.dto.address.request.CreateAddressRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSignupRequest implements RequestDto {

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String password;

    private String confirmPassword;

    private CreateAddressRequest address;
}
