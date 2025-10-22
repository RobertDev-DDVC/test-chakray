package com.test.chakray.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class UserResponseDTO {
    private UUID id;
    private String email;
    private String name;
    private String phone;
    private String taxId;
    private LocalDateTime createdAt;
    private List<AddressResponse> addresses;

    public static class AddressResponse {
        public Integer id;
        public String name;
        public String street;
        public String countryCode;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setAddresses(List<AddressResponse> addresses) {
        this.addresses = addresses;
    }

    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getTaxId() {
        return taxId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<AddressResponse> getAddresses() {
        return addresses;
    }
}
