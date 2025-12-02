package com.Library.Library.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "STAFF")
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String title;

    @Column(name = "password_hash")
    private String passwordHash;

    @Column(unique = true)
    private String email;
}