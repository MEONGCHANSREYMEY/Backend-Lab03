package co.istad.restfulemployeeapi.mapper;

import co.istad.restfulemployeeapi.domain.Department;
import co.istad.restfulemployeeapi.dto.department.CreateDepartmentRequest;
import co.istad.restfulemployeeapi.dto.department.DepartmentResponce;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

// ADDED: new file
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DepartmentMapper {

    DepartmentResponce toResponse(Department department);

    Department fromCreateDepartmentRequest(CreateDepartmentRequest request);

}