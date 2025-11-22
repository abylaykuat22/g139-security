package kz.bitlab.g139market.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class UserUpdateDto {

    @NotEmpty(message = "Username cannot be empty")
    String username;
    @NotEmpty(message = "Full name cannot be empty")
    String fullName;
    LocalDate birthdate;
}
