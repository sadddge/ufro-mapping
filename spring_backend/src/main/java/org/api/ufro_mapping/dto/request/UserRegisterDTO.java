package org.api.ufro_mapping.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDTO {
    @Pattern(regexp = "USER|ADMIN", message = "Role must be USER or ADMIN.")
    private String role;
    @NotBlank(message = "Name cannot be empty.")
    @Size(max = 20, message = "Name must be less than 20 characters.")
    private String name;
    @Email(message = "Invalid email format.")
    @NotBlank(message = "The email cannot be empty.")
    @Size(max = 80, message = "The email must be less than 80 characters.")
    private String email;
    @NotBlank(message = "The password cannot be empty.")
    @Size(max = 200, message = "The password must be less than 200 characters.")
    private String password;
}
