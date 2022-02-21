package com.employee.service.employee;

import com.employee.data.dto.EmployeeDto;
import com.employee.data.models.Employee;
import com.employee.data.repository.EmployeeRepository;
import com.employee.web.exceptions.BusinessLogicException;
import com.employee.web.exceptions.EmployeeDoesNotExistException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    private String generateId(){
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

//        System.out.println(generatedString);
        return generatedString;

    }



    @Autowired
    EmployeeRepository employeeRepository;

    private Employee saveOrUpdate(Employee employee) throws BusinessLogicException {
        if(employee == null){
            throw new BusinessLogicException("Employee cannot be null");

        }
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployee() {
        List<Employee> allEmployee = employeeRepository.findAll();

        return allEmployee;
    }

    @Override
    public Employee findEmployeeById(Long employeeId) throws EmployeeDoesNotExistException {
        if(employeeId == null){
            throw new IllegalArgumentException("Employee ID cannot be null");
        }
        Optional<Employee> queryrepo = employeeRepository.findById(employeeId);
        if(queryrepo.isPresent()){
            return queryrepo.get();
        }
        throw new EmployeeDoesNotExistException("Employee with this ID "+employeeId+" Does not exist");


    }

    @Override
    public Employee createEmployee(EmployeeDto employeeDto) throws BusinessLogicException {
        if(employeeDto == null){
            throw new IllegalArgumentException("Argument cannot be null");
        }
        Optional<Employee> query = employeeRepository.findByFirstName(employeeDto.getFirstName());
        if(query.isPresent()){
            throw new BusinessLogicException("Employee with this firstName: "+employeeDto.getFirstName()+"already exist");
        }

        Employee employee = new Employee(generateId());
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setRole(employeeDto.getRole());
        employee.setEmail(employeeDto.getEmail());


        return employeeRepository.save(employee);
    }

    @Override
    public Employee updateEmployeeDetails(Long employeeId, JsonPatch employeePatch) throws BusinessLogicException{
        Optional<Employee> queryResult = employeeRepository.findById(employeeId);
        if(queryResult.isEmpty()){
            throw new BusinessLogicException("Employee with Id "+ employeeId + "does not exist");

        }
        Employee employeeToUpdate = queryResult.get();

        try{
            employeeToUpdate = applyPatchToEmployee(employeePatch, employeeToUpdate);
            return saveOrUpdate(employeeToUpdate);
        }
        catch (JsonPatchException | JsonProcessingException | BusinessLogicException e){
            throw new BusinessLogicException("Update failed");
        }


    }

    private Employee applyPatchToEmployee(JsonPatch employeePatch, Employee employeeToUpdate) throws JsonPatchException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode patched = employeePatch.apply(objectMapper.convertValue(employeeToUpdate, JsonNode.class));

        return objectMapper.treeToValue(patched, Employee.class);
    }
}
