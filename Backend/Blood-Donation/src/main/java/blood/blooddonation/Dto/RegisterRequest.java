package blood.blooddonation.Dto;

import blood.blooddonation.Enums.BloodType;
import blood.blooddonation.Enums.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private Role role;
    private BloodType bloodType;
    private String city;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
