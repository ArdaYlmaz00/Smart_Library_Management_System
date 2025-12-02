package com.Library.Library.repository;

import com.Library.Library.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    @Procedure(procedureName = "calculate_fine")
    void calculateFine(@Param("p_loan_id") Integer loanId, @Param("daily_fine_amount") BigDecimal dailyFineAmount);
}