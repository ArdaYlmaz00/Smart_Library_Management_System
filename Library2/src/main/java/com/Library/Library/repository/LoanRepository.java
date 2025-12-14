package com.Library.Library.repository;

import com.Library.Library.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByReturnDateIsNull();
    List<Loan> findByMemberIdAndReturnDateIsNull(Long memberId);
    boolean existsByMemberIdAndBookIdAndReturnDateIsNull(Long memberId, Long bookId);
}