package co.istad.restfulemployeeapi.service;

import co.istad.restfulemployeeapi.dto.CreateEmployeeRequest;
import co.istad.restfulemployeeapi.dto.EmployeeResponse;
import co.istad.restfulemployeeapi.dto.UpdateEmployeeRequest;

import java.util.List;

public interface EmployeeService {

    // CRUD: Create, Read, Update, Delete
    List<EmployeeResponse> getAll();
    EmployeeResponse getEmployeeById(String id);

    EmployeeResponse createEmployee(CreateEmployeeRequest employeeRequest);

    EmployeeResponse updateEmployeeById(String id, UpdateEmployeeRequest updateEmployeeRequest);

    void deleteEmployeeById(String id);

    void updateEmployeeStatus(String id, Boolean status);

}
