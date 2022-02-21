package com.employee.data.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@RequiredArgsConstructor
@Data
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private String firstName;
    private String lastName;
    private String role;
    @Column(nullable = false, unique = false)
    private String email;
    private LocalDateTime dateCreated;

    public Employee(String id ){
        this.id = id;
    }

}
