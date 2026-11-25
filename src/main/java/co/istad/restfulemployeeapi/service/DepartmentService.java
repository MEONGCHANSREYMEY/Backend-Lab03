package co.istad.restfulemployeeapi.service;

import co.istad.restfulemployeeapi.dto.EmployeeResponse;
import co.istad.restfulemployeeapi.dto.common.PageResponse;
import co.istad.restfulemployeeapi.dto.department.CreateDepartmentRequest;
import co.istad.restfulemployeeapi.dto.department.DepartmentResponce;
import co.istad.restfulemployeeapi.dto.department.DepartmentStatisticsResponse;

import java.util.List;

// ADDED: new file
public interface DepartmentService {

    // helpers so you can create test data
    DepartmentResponce createDepartment(CreateDepartmentRequest request);

    List<DepartmentResponce> getAllDepartments();

    // 3. Department Employee Listing
    PageResponse<EmployeeResponse> getEmployeesByDepartment(String departmentId,
                                                            int page,
                                                            int size,
                                                            String sortBy,
                                                            String direction);

    // 5. Department Statistics
    DepartmentStatisticsResponse getDepartmentStatistics(String departmentId);

}

