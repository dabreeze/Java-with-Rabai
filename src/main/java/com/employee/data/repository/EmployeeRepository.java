package com.employee.data.repository;

import com.employee.data.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional <Employee> findByFirstName(String firstname);

    Optional<Employee> findByLastName(String lastName);
}
