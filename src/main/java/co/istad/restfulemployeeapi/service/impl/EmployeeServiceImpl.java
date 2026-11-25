package co.istad.restfulemployeeapi.service.impl;

import co.istad.restfulemployeeapi.domain.Employee;
import co.istad.restfulemployeeapi.dto.CreateEmployeeRequest;
import co.istad.restfulemployeeapi.dto.EmployeeResponse;
import co.istad.restfulemployeeapi.dto.UpdateEmployeeRequest;
import co.istad.restfulemployeeapi.mapper.EmployeeMapper;
import co.istad.restfulemployeeapi.repository.EmployeeRepository;
import co.istad.restfulemployeeapi.service.EmployeeService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import co.istad.restfulemployeeapi.domain.Department;
import co.istad.restfulemployeeapi.dto.TransferEmployeeRequest;
import co.istad.restfulemployeeapi.repository.DepartmentRepository;

@Service
@RequiredArgsConstructor
//@Transactional(readOnly = true)
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final DepartmentRepository departmentRepository; // ADDED


    @Override
    public List<EmployeeResponse> getAll() {
        return List.of();
    }

    @Override
    public EmployeeResponse getEmployeeById(String id) {

        Employee emp = getById(id);

        return employeeMapper.toResponse(emp);

    }

    @Transactional
    @Override
    public EmployeeResponse createEmployee(CreateEmployeeRequest employeeRequest) {

        if (employeeRepository.existsByEmail(employeeRequest.email())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Email is already existed"
            );
        }

        Employee emp = employeeMapper.fromCreateEmployeeRequest(employeeRequest);
        emp.setIsActive(Boolean.TRUE);

        // ADDED: put the new employee in a department (optional)
        if (employeeRequest.departmentId() != null && !employeeRequest.departmentId().isBlank()) {
            emp.setDepartment(getDepartmentById(employeeRequest.departmentId()));
        }

        employeeRepository.save(emp);

        return employeeMapper.toResponse(emp);

    }

    @Override
    public EmployeeResponse updateEmployeeById(String id, UpdateEmployeeRequest updateEmployeeRequest) {
        return null;
    }

    @Override
    public void deleteEmployeeById(String id) {

    }

    @Transactional
    @Override
    public void updateEmployeeStatus(String id, Boolean status) {

        checkIdExists(id);

        employeeRepository.updateEmployeeStatus(id, status);
    }

    // ===================== ADDED for the assignment =====================

    // 1. Advanced Employee Search
    @Override
    public List<EmployeeResponse> searchEmployees(String keyword) {

        if (keyword == null || keyword.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Keyword must not be blank"
            );
        }

        return employeeRepository.searchByNameOrEmail(keyword.trim())
                .stream()
                .map(employeeMapper::toResponse)
                .toList();
    }

    // 4. Employee Transfer
    @Transactional
    @Override
    public EmployeeResponse transferEmployee(String employeeId, TransferEmployeeRequest request) {

        Employee emp = getById(employeeId);

        // validate: destination department must exist ...
        Department destination = getDepartmentById(request.departmentId());

        // ... and must be active
        if (!Boolean.TRUE.equals(destination.getIsActive())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Destination department is not active"
            );
        }

        // the employee is already in this department
        if (emp.getDepartment() != null
                && emp.getDepartment().getId().equals(destination.getId())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Employee is already in this department"
            );
        }

        emp.setDepartment(destination);
        employeeRepository.save(emp);

        return employeeMapper.toResponse(emp);
    }

    private Department getDepartmentById(String departmentId) {
        return departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Department has not been found"
                ));
    }

    // ====================================================================

    private void checkIdExists(String id) {
        if(!employeeRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Employee has not been found"
            );
        }
    }

    private Employee getById(String id) {
        return employeeRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Employee has not been found"
                        )
                );
    }

}
