package co.istad.restfulemployeeapi.controller;

import co.istad.restfulemployeeapi.dto.EmployeeResponse;
import co.istad.restfulemployeeapi.dto.common.PageResponse;
import co.istad.restfulemployeeapi.dto.department.CreateDepartmentRequest;
import co.istad.restfulemployeeapi.dto.department.DepartmentResponce;
import co.istad.restfulemployeeapi.dto.department.DepartmentStatisticsResponse;
import co.istad.restfulemployeeapi.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// ADDED: new file
@RestController
@RequestMapping("/api/v1/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService service;

    // helper: create a department so you have data to test with
    @PostMapping
    public DepartmentResponce createDepartment(@Valid @RequestBody CreateDepartmentRequest request) {
        return service.createDepartment(request);
    }

    @GetMapping
    public List<DepartmentResponce> getAllDepartments() {
        return service.getAllDepartments();
    }

    // 3. Department Employee Listing
    // GET /api/v1/departments/{departmentId}/employees?page=0&size=10&sortBy=name&direction=asc
    @GetMapping("/{departmentId}/employees")
    public PageResponse<EmployeeResponse> getEmployeesByDepartment(
            @PathVariable String departmentId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        return service.getEmployeesByDepartment(departmentId, page, size, sortBy, direction);
    }

    // 5. Department Statistics
    // GET /api/v1/departments/{departmentId}/statistics
    @GetMapping("/{departmentId}/statistics")
    public DepartmentStatisticsResponse getDepartmentStatistics(@PathVariable String departmentId) {
        return service.getDepartmentStatistics(departmentId);
    }

}

