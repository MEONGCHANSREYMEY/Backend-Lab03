package co.istad.restfulemployeeapi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "borrowings")
public class Borrowing {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private LocalDateTime borrowedAt;

    private LocalDateTime dueDate;

    private LocalDateTime returnedAt;

    // many borrowings -> one book (creates borrowings.book_id)
    @ManyToOne(optional = false)
    @JoinColumn(name = "book_id")
    private Book book;

    // many borrowings -> one member (creates borrowings.member_id)
    @ManyToOne(optional = false)
    @JoinColumn(name = "member_id")
    private Member member;
}