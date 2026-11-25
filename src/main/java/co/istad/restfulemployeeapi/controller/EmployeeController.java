package co.istad.restfulemployeeapi.controller;

import co.istad.restfulemployeeapi.dto.CreateEmployeeRequest;
import co.istad.restfulemployeeapi.dto.EmployeeResponse;
import co.istad.restfulemployeeapi.service.EmployeeService;
import io.swagger.v3.oas.models.security.SecurityScheme;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import co.istad.restfulemployeeapi.dto.TransferEmployeeRequest;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService service;

    @GetMapping
    public List<EmployeeResponse> getAllEmployees() {
        return service.getAll();
    }

    // ADDED: 1. Advanced Employee Search
    // GET /api/v1/employees/search?keyword=john
    @GetMapping("/search")
    public List<EmployeeResponse> searchEmployees(@RequestParam String keyword) {
        return service.searchEmployees(keyword);
    }

    // ADDED: 4. Employee Transfer
    // PATCH /api/v1/employees/{employeeId}/transfer   body: { "departmentId": "..." }
    @PatchMapping("/{employeeId}/transfer")
    public EmployeeResponse transferEmployee(@PathVariable String employeeId,
                                             @Valid @RequestBody TransferEmployeeRequest request) {
        return service.transferEmployee(employeeId, request);
    }

    @GetMapping("/{id}")
    public EmployeeResponse getEmployeeById(@PathVariable String id) {
        return service.getEmployeeById(id);
    }

    @PutMapping("/{id}")
    public void updateEmployeeStatus(@PathVariable String id,
                                     @RequestParam Boolean isActive) {
        service.updateEmployeeStatus(id, isActive);
    }

    @PostMapping
    public EmployeeResponse createEmployee(
            @Valid @RequestBody CreateEmployeeRequest createRequest
    ) {
        return service.createEmployee(createRequest);
    }

}
