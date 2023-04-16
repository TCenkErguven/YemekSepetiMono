package com.yemeksepeti.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDto {
    private String name;
    private String surname;
    private String email;
    private String address;
    private String password;
    private String rePassword;
    private String phoneNumber;
    private String cardDetails;
}
