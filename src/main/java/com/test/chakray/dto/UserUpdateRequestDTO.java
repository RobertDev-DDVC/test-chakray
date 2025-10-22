package com.test.chakray.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;

import java.time.LocalDateTime;
import java.util.List;

public class UserUpdateRequestDTO {
    @Email
    private String email;

    private String name;
    private String phone;
    private String password;

    @JsonProperty("tax_id")
    private String taxId;

    @JsonProperty("created_at")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm", timezone = "Indian/Antananarivo")
    private LocalDateTime createdAt;

    private List<@Valid AddressRequestDTO> addresses;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String gettaxId() {
        return taxId;
    }

    public void settaxId(String taxId) {
        this.taxId = taxId;
    }

    public LocalDateTime getcreatedAt() {
        return createdAt;
    }

    public void setcreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<AddressRequestDTO> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressRequestDTO> addresses) {
        this.addresses = addresses;
    }
}
