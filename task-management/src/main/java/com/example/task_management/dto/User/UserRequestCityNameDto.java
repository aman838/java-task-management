package com.example.task_management.dto.User;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestCityNameDto {
    @NotNull
    private String city;
}
