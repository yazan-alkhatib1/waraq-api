package com.waraq.dto.admin;


import com.waraq.dto.CreateDTO;
import com.waraq.repository.enums.Role;
import lombok.*;

@Getter
@Setter
@Builder(toBuilder = true)
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest implements CreateDTO {

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String password;
    private Role role;
}
