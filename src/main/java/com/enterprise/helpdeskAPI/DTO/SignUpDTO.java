package com.enterprise.helpdeskAPI.DTO;

import com.enterprise.helpdeskAPI.enums.ProfileEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SignUpDTO {
    private String username;
    private String email;
    private String password;
    private ProfileEnum profile;
}
