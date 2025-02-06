package org.example.tm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.tm.entity.enums.Role;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "username", unique = true, nullable = false)
    private String username;
    @Column(name = "password",unique = true, nullable = false)
    private String password;
    @Column(name = "column", nullable = false)
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;

}
