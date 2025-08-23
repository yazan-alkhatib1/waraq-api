package com.waraq.dto.admin.profile;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProfileRequest {

    private String firstName;

    private String lastName;
}
