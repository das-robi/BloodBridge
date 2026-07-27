package com.robindas.bloodbridge.DTO;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank
    private String userName;
    @NotBlank
    @Email
    private String userEmail;

    @NotBlank
    @Size(min = 8, max = 20)
    private String passWord;

}
