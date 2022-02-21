package com.employee.service.employee;

import com.employee.data.dto.EmployeeDto;
import com.employee.data.models.Employee;
import com.employee.web.exceptions.BusinessLogicException;
import com.employee.web.exceptions.EmployeeDoesNotExistException;
import com.github.fge.jsonpatch.JsonPatch;




import java.util.List;

public interface EmployeeService {

    List<Employee> getAllEmployee();
    Employee findEmployeeById (Long employeeId) throws EmployeeDoesNotExistException;
    Employee createEmployee(EmployeeDto employeeDto) throws BusinessLogicException;
    Employee updateEmployeeDetails(Long employeeId, JsonPatch employeePatch) throws BusinessLogicException;
}
