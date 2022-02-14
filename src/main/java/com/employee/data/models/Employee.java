package com.employee.data.models;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private String role;
    @Column(nullable = false, unique = false)
    private String email;
    private LocalDateTime dateCreated;
}
