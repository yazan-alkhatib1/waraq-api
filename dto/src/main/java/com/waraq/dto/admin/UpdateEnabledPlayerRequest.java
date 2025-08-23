package com.waraq.dto.admin;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEnabledPlayerRequest {

    private Long id;

    private Boolean isEnabled;
}
