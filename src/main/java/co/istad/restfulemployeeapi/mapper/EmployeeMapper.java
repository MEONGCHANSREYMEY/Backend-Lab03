package co.istad.restfulemployeeapi.mapper;

import co.istad.restfulemployeeapi.domain.Employee;
import co.istad.restfulemployeeapi.dto.CreateEmployeeRequest;
import co.istad.restfulemployeeapi.dto.EmployeeResponse;
import co.istad.restfulemployeeapi.dto.UpdateEmployeeRequest;
import org.mapstruct.*;

// ADDED: uses = DepartmentMapper.class -> maps employee.department into DepartmentResponce
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = DepartmentMapper.class)
public interface EmployeeMapper {

    // Map from Domain to DTO
    EmployeeResponse toResponse(Employee employee);

    // Map from DTO to Domain
    Employee fromCreateEmployeeRequest(CreateEmployeeRequest createEmployeeRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget Employee employee,
                      UpdateEmployeeRequest request);

}
