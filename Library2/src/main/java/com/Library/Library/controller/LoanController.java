package com.Library.Library.controller;

import com.Library.Library.entity.Loan;
import com.Library.Library.service.LoanService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }
    @GetMapping("/active")
    public List<Loan> getActiveLoans() {
        return loanService.getActiveLoansWithFines();
    }
    @PostMapping("/loan/{bookId}/{memberId}")
    public Loan loanBook(@PathVariable Long bookId, @PathVariable Long memberId) {
        return loanService.loanBook(bookId, memberId);
    }
    @PutMapping("/return/{loanId}")
    public Loan returnBook(@PathVariable Long loanId) {
        return loanService.returnBook(loanId);
    }
    @GetMapping("/member/{memberId}")
    public List<Loan> getMemberLoans(@PathVariable Long memberId) {
        return loanService.getMemberLoans(memberId);
    }
}