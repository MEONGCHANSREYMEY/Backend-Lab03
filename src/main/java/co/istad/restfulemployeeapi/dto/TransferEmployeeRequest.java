package co.istad.restfulemployeeapi.dto;

import jakarta.validation.constraints.NotBlank;

// ADDED: new file - request body for PATCH /employees/{employeeId}/transfer
public record TransferEmployeeRequest(

        @NotBlank(message = "Destination department ID is required")
        String departmentId

) {
}

