package com.allybros.taskcase.security;

public enum UserRole {
    STD_USER ("STD_USER"),
    ADMIN ("ADMIN");

    private String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
