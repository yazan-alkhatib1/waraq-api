package com.waraq.dto.admin;

import com.waraq.dto.CreateDTO;
import com.waraq.repository.enums.Role;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AdminCreateUserRequest implements CreateDTO {

    private Long imageId;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String email;
    private Role role;
}
