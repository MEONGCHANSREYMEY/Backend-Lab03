package co.istad.restfulemployeeapi.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record EmployeeResponse(

    String id,

    String name,

    String email,

    BigDecimal salary,

    LocalDate hireDate,

    Boolean isActive

) {
}
