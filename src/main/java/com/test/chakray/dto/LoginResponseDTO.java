package com.test.chakray.dto;

public class LoginResponseDTO {
    private String message;
    private String userId;
    private String taxId;
    private String name;
    private String email;

    public LoginResponseDTO(String message, String userId, String taxId, String name, String email) {
        this.message = message;
        this.userId = userId;
        this.taxId = taxId;
        this.name = name;
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public String getUserId() {
        return userId;
    }

    public String getTaxId() {
        return taxId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
