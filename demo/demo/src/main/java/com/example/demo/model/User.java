package com.example.demo.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;
    private String gender;
    private String phone;
    private String title;
    private String imageSource;
    private String token;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private Integer isDeleted;
    private String newPassword;
    private String confirmPassword;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE)
    private List<Study> studies = new ArrayList<>();

    public Set<Role> getRoles() {
        return roles;
    }
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                ", phone='" + phone + '\'' +
                ", title='" + title + '\'' +
                ", imageSource='" + imageSource + '\'' +
                ", token='" + token + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", isDeleted=" + isDeleted +
                ", roles=" + roles +
                ", studies=" + studies +
                '}';
    }
}
