package com.waraq.dto.auth.request;

import com.waraq.dto.RequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest implements RequestDto {

    private String email;

    private String password;
}
