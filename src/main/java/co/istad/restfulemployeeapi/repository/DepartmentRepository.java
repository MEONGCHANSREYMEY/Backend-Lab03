package co.istad.restfulemployeeapi.repository;

import co.istad.restfulemployeeapi.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;

// ADDED: completed (was "extends {" which does not compile)
public interface DepartmentRepository extends JpaRepository<Department, String> {

    boolean existsByNameIgnoreCase(String name);

}
