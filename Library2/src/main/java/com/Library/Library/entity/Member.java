package com.Library.Library.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "MEMBERS")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;
    private String token;
}