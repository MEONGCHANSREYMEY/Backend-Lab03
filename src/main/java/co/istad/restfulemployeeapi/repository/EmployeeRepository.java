package co.istad.restfulemployeeapi.repository;

import co.istad.restfulemployeeapi.domain.Employee;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeRepository extends JpaRepository<Employee, String> {

    boolean existsByEmail(String email);

    List<Employee> findBySalaryGreaterThan(BigDecimal salary);

    List<Employee> findByNameStartsWithIgnoreCase(String name);

    // Native SQL
    @Query(value = "select * from employees where name = :name", nativeQuery = true)
    Optional<Employee> getByName(String name);

    // JPQL
    @Query(value = "select e from Employee  e where e.hireDate = :hireDate")
    Optional<Employee> getByHireDate(LocalDate hireDate);

    @Modifying
    @Query(value = "update Employee e set e.isActive = :status where e.id = :id")
    void updateEmployeeStatus(String id, Boolean status);

    List<Employee> id(String id);

    // ===================== ADDED for the assignment =====================

    // 1. Advanced search: case-insensitive partial match on name OR email
    @Query("""
            select e from Employee e
            left join fetch e.department
            where lower(e.name)  like lower(concat('%', :keyword, '%'))
               or lower(e.email) like lower(concat('%', :keyword, '%'))
            order by e.name asc
            """)
    List<Employee> searchByNameOrEmail(String keyword);

    // 3. Department employee listing with pagination + sorting
    Page<Employee> findByDepartmentId(String departmentId, Pageable pageable);

    // 5. Department statistics
    long countByDepartmentId(String departmentId);

    long countByDepartmentIdAndIsActiveTrue(String departmentId);

    @Query("select avg(e.salary) from Employee e where e.department.id = :departmentId")
    Double averageSalaryByDepartmentId(String departmentId);

}
