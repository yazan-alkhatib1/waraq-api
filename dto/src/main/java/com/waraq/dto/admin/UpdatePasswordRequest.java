package com.waraq.dto.admin;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePasswordRequest {

    private Long id;

    private String newPassword;

    private String confirmPassword;
}
