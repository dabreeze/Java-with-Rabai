package com.employee.web.controllers;

import com.employee.data.dto.EmployeeDto;
import com.employee.data.repository.EmployeeRepository;
import com.employee.service.employee.EmployeeService;
import com.github.fge.jsonpatch.JsonPatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employee")
public class EmployeeRestController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping("/search")
    public ResponseEntity<?>findAll(){return null;}

    @PostMapping("/api/create")
    public ResponseEntity<?> createEmployee(@RequestBody EmployeeDto employeeDto){return  null;}

    @PatchMapping(value = "/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<?> updateEmployee(@PathVariable Long id, @RequestBody JsonPatch employeePatch){return null;}



}
