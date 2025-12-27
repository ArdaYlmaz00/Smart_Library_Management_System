package com.Library.Library.service;

import com.Library.Library.entity.Book;
import com.Library.Library.entity.Loan;
import com.Library.Library.entity.Member;
import com.Library.Library.repository.BookRepository;
import com.Library.Library.repository.LoanRepository;
import com.Library.Library.repository.MemberRepository;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private static final double MINUTE_FINE = 0.5;

    public LoanService(LoanRepository loanRepository, BookRepository bookRepository, MemberRepository memberRepository) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
    }

    public List<Loan> getActiveLoansWithFines() {
        List<Loan> activeLoans = loanRepository.findByReturnDateIsNull();
        for (Loan loan : activeLoans) {
            loan.setFineAmount(calculateFine(loan));
        }
        return activeLoans;
    }

    @Transactional
    public Loan returnBook(Long loanId) {
        Loan loan = loanRepository.findById(loanId).orElseThrow(() -> new RuntimeException("Ödünç kaydı bulunamadı!"));
        loan.setReturnDate(LocalDateTime.now());

        Book book = loan.getBook();
        book.setStockQuantity(book.getStockQuantity() + 1);
        bookRepository.save(book);

        loan.setFineAmount(calculateFine(loan));
        return loanRepository.save(loan);
    }

    @Transactional
    public Loan loanBook(Long bookId, Long memberId) {
        if (loanRepository.existsByMemberIdAndBookIdAndReturnDateIsNull(memberId, bookId)) {
            throw new RuntimeException("Bu kitabı zaten ödünç aldınız!");
        }
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Kitap bulunamadı."));
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("Üye bulunamadı."));

        if (book.getStockQuantity() <= 0) {
            throw new RuntimeException("Stok yok.");
        }

        book.setStockQuantity(book.getStockQuantity() - 1);
        bookRepository.save(book);

        Loan loan = new Loan();
        loan.setBook(book);
        loan.setMember(member);

        loan.setLoanDate(LocalDateTime.now());
        loan.setDueDate(LocalDateTime.now().plusMinutes(1)); // 1 Dakika süre

        return loanRepository.save(loan);
    }

    private Double calculateFine(Loan loan) {
        LocalDateTime now = LocalDateTime.now();
        if (loan.getDueDate().isBefore(now)) {
            long overdueMinutes = ChronoUnit.MINUTES.between(loan.getDueDate(), now);
            return overdueMinutes * MINUTE_FINE;
        }
        return 0.0;
    }

    public List<Loan> getMemberLoans(Long memberId) {
        List<Loan> loans = loanRepository.findByMemberIdAndReturnDateIsNull(memberId);

        LocalDateTime now = LocalDateTime.now();

        for (Loan loan : loans) {
            loan.setFineAmount(calculateFine(loan));

            if (loan.getDueDate().isBefore(now)) {
                loan.setDueDate(now.minusDays(1));
            }
        }
        return loans;
    }
}