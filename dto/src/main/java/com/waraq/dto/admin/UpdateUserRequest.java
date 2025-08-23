package com.waraq.dto.admin;
import com.waraq.dto.UpdateDTO;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest implements UpdateDTO {

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;
}
