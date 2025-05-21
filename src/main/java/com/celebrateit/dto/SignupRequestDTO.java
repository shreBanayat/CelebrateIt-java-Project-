package com.celebrateit.dto;

import com.celebrateit.pojo.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignupRequestDTO {
    public String userName;
    public String email;
    public String password;
    public String contact_number;
    public UserRole role;
}
