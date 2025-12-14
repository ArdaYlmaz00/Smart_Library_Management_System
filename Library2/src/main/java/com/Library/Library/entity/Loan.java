package com.Library.Library.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "LOAN")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member; // Ödünç alan üye

    @ManyToOne
    @JoinColumn(name = "staff_id")
    private Staff staff;

    @Column(name = "loan_date", nullable = false)
    private LocalDate loanDate = LocalDate.now();

    @Column(name = "return_date")
    private LocalDate returnDate;

    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @Column(name = "fine_amount")
    private Double fineAmount = 0.0;

    @PrePersist
    public void prePersist() {
        if (this.dueDate == null) {
            this.dueDate = LocalDate.now().plusDays(14);
        }
    }
}