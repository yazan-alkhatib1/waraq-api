package com.waraq.dto.admin;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.waraq.dto.ResponseDTO;
import com.waraq.repository.enums.Role;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdminUserResponse implements ResponseDTO {

    private Long id;

    private LocalDateTime creationDate;

    private Boolean isEnabled;

    private Long imageId;

    private String imageUrl;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String email;
    private Role role;

}
