package co.istad.restfulemployeeapi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String title;

    private String author;

    @Column(unique = true, length = 20)
    private String isbn;

    private String description;

    private Integer quantity;

    private Boolean isActive;

    // many books -> one category (creates books.category_id)
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}