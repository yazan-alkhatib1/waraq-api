package com.waraq.repository.enums;

public enum Role {

    CLIENT("client"),TRANSLATOR("translator"),ADMIN("admin");

    private final String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return this.role;
    }
}
