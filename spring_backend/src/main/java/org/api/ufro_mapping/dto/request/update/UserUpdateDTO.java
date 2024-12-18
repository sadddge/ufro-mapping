package org.api.ufro_mapping.dto.request.update;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.api.ufro_mapping.models.Role;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDTO {
    private Role role;
    @Pattern(regexp = "^(?!\\s*$).+", message = "Name cannot be empty.")
    @Size(max = 20, message = "Name must be less than 20 characters.")
    private String name;
    @Email(message = "Invalid email format.")
    @Pattern(regexp = "^(?!\\s*$).+", message = "Email cannot be empty.")
    @Size(max = 80, message = "The email must be less than 80 characters.")
    private String email;
    @Pattern(regexp = "^(?!\\s*$).+", message = "Password cannot be empty.")
    @Size(max = 200, message = "The password must be less than 200 characters.")
    private String password;
}
