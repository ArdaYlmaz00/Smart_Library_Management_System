package com.Library.Library.service;

import com.Library.Library.entity.Book;
import com.Library.Library.entity.Loan;
import com.Library.Library.entity.Member;
import com.Library.Library.repository.BookRepository;
import com.Library.Library.repository.LoanRepository;
import com.Library.Library.repository.MemberRepository;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private static final double DAILY_FINE = 0.5; // Günlük ceza 0.5 TL

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

        loan.setReturnDate(LocalDate.now());

        Book book = loan.getBook();
        book.setStockQuantity(book.getStockQuantity() + 1);
        bookRepository.save(book);

        loan.setFineAmount(calculateFine(loan));
        return loanRepository.save(loan);
    }

    @Transactional
    public Loan loanBook(Long bookId, Long memberId) {
        if (loanRepository.existsByMemberIdAndBookIdAndReturnDateIsNull(memberId, bookId)) {
            throw new RuntimeException("Bu kitabı zaten ödünç aldınız! İade etmeden tekrar alamazsınız.");
        }
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Kitap bulunamadı."));
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("Üye bulunamadı."));

        if (book.getStockQuantity() <= 0) {
            throw new RuntimeException("Bu kitabın stoğu kalmamıştır.");
        }

        book.setStockQuantity(book.getStockQuantity() - 1);
        bookRepository.save(book);

        Loan loan = new Loan();
        loan.setBook(book);
        loan.setMember(member);

        return loanRepository.save(loan);
    }

    private Double calculateFine(Loan loan) {
        LocalDate today = LocalDate.now();
        if (loan.getDueDate().isBefore(today)) {
            long overdueDays = ChronoUnit.DAYS.between(loan.getDueDate(), today);
            return overdueDays * DAILY_FINE;
        }
        return 0.0;
    }

    public long getRemainingDays(Loan loan) {
        return ChronoUnit.DAYS.between(LocalDate.now(), loan.getDueDate());
    }

    public List<Loan> getMemberLoans(Long memberId) {
        List<Loan> loans = loanRepository.findByMemberIdAndReturnDateIsNull(memberId);
        for (Loan loan : loans) {
            loan.setFineAmount(calculateFine(loan));
        }
        return loans;
    }

}