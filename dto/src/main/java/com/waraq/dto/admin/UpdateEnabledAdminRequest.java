package com.waraq.dto.admin;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEnabledAdminRequest {

    private Long id;

    private Boolean isEnabled;
}
