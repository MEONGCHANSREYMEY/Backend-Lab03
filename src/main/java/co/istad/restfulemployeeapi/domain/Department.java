package co.istad.restfulemployeeapi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// ADDED: Department entity (was an empty class)
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    private String description;

    // Only ACTIVE departments can receive transferred employees
    @Column(nullable = false)
    private Boolean isActive;

}
