package com.example.task_management.dto.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    @NotBlank(message = "Username is Rrquired")
    @Size(min = 6,message = "Username should have minimum 6 characters long")
    private String username;

    @NotBlank(message = "Password is Required")
    @Size(min = 6, message = "Password should be 6 character long")
    private String password;
}
