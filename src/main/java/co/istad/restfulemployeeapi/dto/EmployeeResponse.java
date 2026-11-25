package co.istad.restfulemployeeapi.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import co.istad.restfulemployeeapi.dto.department.DepartmentResponce;

public record EmployeeResponse(

        String id,

        String name,

        String email,

        BigDecimal salary,

        LocalDate hireDate,

        Boolean isActive,

        // ADDED: department info (null if the employee has no department yet)
        DepartmentResponce department

) {
}