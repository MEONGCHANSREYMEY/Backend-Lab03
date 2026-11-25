package co.istad.restfulemployeeapi.dto.department;

import lombok.Builder;

import java.math.BigDecimal;

// ADDED: new file
@Builder
public record DepartmentStatisticsResponse(

        String departmentId,

        String departmentName,

        Long totalEmployees,

        Long activeEmployees,

        BigDecimal averageSalary

) {
}

