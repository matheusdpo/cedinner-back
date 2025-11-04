package br.com.matheusdpo.cedinner.domain.entities;

import br.com.matheusdpo.cedinner.domain.valueobjects.UserId;

public class User {
    private UserId id;
    private String name;
    private String username;
    private String email;
    private String phone;
    private String address;

    public User(String name, String username, String email, String phone, String address) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }

        this.name = name;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public User(UserId id, String name, String username, String email, String phone, String address) {
        this(name, username, email, phone, address);
        this.id = id;
    }

    public void updateProfile(String name, String email, String phone, String address) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name;
        }
        if (email != null && !email.trim().isEmpty()) {
            this.email = email;
        }
        this.phone = phone;
        this.address = address;
    }

    public void updateAddress(String newAddress) {
        this.address = newAddress;
    }

    // Getters
    public UserId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    // Setters for infrastructure layer
    public void setId(UserId id) {
        this.id = id;
    }
}
