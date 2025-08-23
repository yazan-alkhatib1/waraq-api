package com.waraq.dto.admin.profile;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProfilePasswordRequest {

    private String oldPassword;

    private String newPassword;

    private String confirmPassword;
}