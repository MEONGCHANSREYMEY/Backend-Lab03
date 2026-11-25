package co.istad.restfulemployeeapi.service.impl;

import co.istad.restfulemployeeapi.domain.Department;
import co.istad.restfulemployeeapi.dto.EmployeeResponse;
import co.istad.restfulemployeeapi.dto.common.PageResponse;
import co.istad.restfulemployeeapi.dto.department.CreateDepartmentRequest;
import co.istad.restfulemployeeapi.dto.department.DepartmentResponce;
import co.istad.restfulemployeeapi.dto.department.DepartmentStatisticsResponse;
import co.istad.restfulemployeeapi.mapper.DepartmentMapper;
import co.istad.restfulemployeeapi.mapper.EmployeeMapper;
import co.istad.restfulemployeeapi.repository.DepartmentRepository;
import co.istad.restfulemployeeapi.repository.EmployeeRepository;
import co.istad.restfulemployeeapi.service.DepartmentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Set;

// ADDED: completed (was unfinished: "private final" with nothing after it)
@Service
@RequiredArgsConstructor
public class DeapartmentServiceIMPL implements DepartmentService {

    // Only these Employee fields may be used in ?sortBy=...
    // (an unknown field would otherwise crash with a 500 error)
    private static final Set<String> ALLOWED_SORT_FIELDS =
            Set.of("name", "email", "salary", "hireDate", "isActive");

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    private final DepartmentMapper departmentMapper;
    private final EmployeeMapper employeeMapper;

    // helper: create a department so you have data to test with
    @Transactional
    @Override
    public DepartmentResponce createDepartment(CreateDepartmentRequest request) {

        if (departmentRepository.existsByNameIgnoreCase(request.name())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Department name already exists"
            );
        }

        Department department = departmentMapper.fromCreateDepartmentRequest(request);
        if (department.getIsActive() == null) {
            department.setIsActive(Boolean.TRUE);
        }

        departmentRepository.save(department);

        return departmentMapper.toResponse(department);
    }

    @Override
    public List<DepartmentResponce> getAllDepartments() {
        return departmentRepository.findAll()
                .stream()
                .map(departmentMapper::toResponse)
                .toList();
    }

    // 3. Department Employee Listing
    @Override
    public PageResponse<EmployeeResponse> getEmployeesByDepartment(String departmentId,
                                                                   int page,
                                                                   int size,
                                                                   String sortBy,
                                                                   String direction) {

        checkDepartmentExists(departmentId);

        if (page < 0 || size < 1 || size > 100) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "page must be >= 0 and size must be between 1 and 100");
        }
        if (!ALLOWED_SORT_FIELDS.contains(sortBy)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "sortBy must be one of " + ALLOWED_SORT_FIELDS);
        }

        Sort.Direction sortDirection = Sort.Direction.fromOptionalString(direction)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "direction must be asc or desc"));

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

        return PageResponse.from(
                employeeRepository.findByDepartmentId(departmentId, pageable)
                        .map(employeeMapper::toResponse)
        );
    }

    // 5. Department Statistics
    @Override
    public DepartmentStatisticsResponse getDepartmentStatistics(String departmentId) {

        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Department has not been found"
                ));

        long total = employeeRepository.countByDepartmentId(departmentId);
        long active = employeeRepository.countByDepartmentIdAndIsActiveTrue(departmentId);
        Double avg = employeeRepository.averageSalaryByDepartmentId(departmentId);

        // avg is null when the department has no employees -> show 0.00
        BigDecimal averageSalary = (avg == null)
                ? BigDecimal.ZERO.setScale(2)
                : BigDecimal.valueOf(avg).setScale(2, RoundingMode.HALF_UP);

        return DepartmentStatisticsResponse.builder()
                .departmentId(department.getId())
                .departmentName(department.getName())
                .totalEmployees(total)
                .activeEmployees(active)
                .averageSalary(averageSalary)
                .build();
    }

    private void checkDepartmentExists(String departmentId) {
        if (!departmentRepository.existsById(departmentId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Department has not been found"
            );
        }
    }

}

