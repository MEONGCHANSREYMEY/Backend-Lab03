package co.istad.restfulemployeeapi.dto.department;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// ADDED: fields (was an empty record)
public record CreateDepartmentRequest(

        @NotBlank(message = "Department name is required")
        @Size(max = 100, message = "Department name must not exceed 100 characters")
        String name,

        String description,

        // optional - defaults to true when not sent
        Boolean isActive

) {
}

