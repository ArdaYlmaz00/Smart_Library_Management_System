package com.Library.Library.controller;

import com.Library.Library.entity.Book; // Eklendi
import com.Library.Library.entity.Loan;
import com.Library.Library.service.LoanService;
import com.Library.Library.repository.LoanRepository;
import com.Library.Library.repository.BookRepository; // Eklendi
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService loanService;
    private final LoanRepository loanRepository;
    private final BookRepository bookRepository; // Stok artırmak için lazım

    // Constructor'a BookRepository ekledik
    public LoanController(LoanService loanService, LoanRepository loanRepository, BookRepository bookRepository) {
        this.loanService = loanService;
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
    }

    @GetMapping("/active")
    public List<Loan> getActiveLoans() {
        return loanService.getActiveLoansWithFines();
    }

    @PostMapping("/loan/{bookId}/{memberId}")
    public Loan loanBook(@PathVariable Long bookId, @PathVariable Long memberId) {
        return loanService.loanBook(bookId, memberId);
    }

    @GetMapping("/member/{memberId}")
    public List<Loan> getMemberLoans(@PathVariable Long memberId) {
        return loanService.getMemberLoans(memberId);
    }

    @PostMapping("/return/{loanId}")
    public org.springframework.http.ResponseEntity<?> returnBook(@PathVariable Long loanId) {
        try {
            loanService.returnBook(loanId);
            return org.springframework.http.ResponseEntity.ok("Kitap iade edildi.");
        } catch (Exception e) {
            return org.springframework.http.ResponseEntity.badRequest().body("Hata: " + e.getMessage());
        }
    }

    // --- BURASI DEĞİŞTİ: ÖDEME YAPINCA KİTABI İADE ALIYORUZ ---
    @PutMapping("/pay-fine/{loanId}")
    public org.springframework.http.ResponseEntity<?> payFine(@PathVariable Long loanId) {
        return loanRepository.findById(loanId).map(loan -> {

            // 1. Cezayı Sıfırla
            loan.setFineAmount(0.0);

            // 2. Kitabı İADE EDİLDİ say (Bugünün tarihini at)
            // Tarih atılınca "ReturnDateIsNull" sorgusundan düşer ve listeden gider.
            loan.setReturnDate(LocalDateTime.now());

            // 3. Kitap Stoğunu 1 Artır
            Book book = loan.getBook();
            book.setStockQuantity(book.getStockQuantity() + 1);
            bookRepository.save(book);

            loanRepository.save(loan);

            return org.springframework.http.ResponseEntity.ok("Ödeme alındı ve kitap iade edildi.");
        }).orElseThrow(() -> new RuntimeException("Kayıt yok"));
    }
}