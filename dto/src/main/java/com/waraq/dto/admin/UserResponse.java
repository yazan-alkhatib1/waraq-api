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
public class UserResponse implements ResponseDTO {

    private Long id;

    private Long userId;

    private Boolean isEnabled;

    private LocalDateTime creationDate;

    private LocalDateTime updateDate;

    private Long createdById;

    private Long updatedById;

    private String firstName;

    private String lastName;

    private String userName;

    private String email;
    private Role role;

}
