package blood.blooddonation.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class LoginRequest {
    @NotBlank(message = "Email is Required")
    private String email;
    @NotBlank(message = "Password is Required")
    private String password;

    public @NotBlank(message = "Email is Required") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Email is Required") String email) {
        this.email = email;
    }

    public @NotBlank(message = "Password is Required") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "Password is Required") String password) {
        this.password = password;
    }
    /*
    How To Use REGEX To Specify Pattern of Email
    @Pattern(

                    regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
                    message = "Invalid email format"

    )*/


}
