package com.Library.Library.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "FINE")
public class Fine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "loan_id")
    private Loan loan;

    @Column(name = "fine_amount")
    private BigDecimal fineAmount;

    @Column(name = "fine_date")
    private LocalDate fineDate;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    private String status;
}