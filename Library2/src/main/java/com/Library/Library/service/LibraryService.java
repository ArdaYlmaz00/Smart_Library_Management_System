package com.Library.Library.service;

import com.Library.Library.controller.LoanRequest;
import com.Library.Library.entity.*;
import com.Library.Library.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime; // Eklendi: Saat/Dakika için
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class LibraryService {

    @Autowired private LoanRepository loanRepository;
    @Autowired private BookRepository bookRepository;
    @Autowired private MemberRepository memberRepository;
    @Autowired private StaffRepository staffRepository;
    @Autowired private FineRepository fineRepository;
    @Autowired private CategoryRepository categoryRepository;

    public List<Book> getAllBooks() { return bookRepository.findAll(); }
    public List<Category> getAllCategories() { return categoryRepository.findAll(); }
    public List<Member> getAllMembers() { return memberRepository.findAll(); }
    public List<Loan> getAllLoans() { return loanRepository.findAll(); }

    public Book addBook(Book book) { return bookRepository.save(book); }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public Loan borrowBook(Long bookId, Long memberId, Long staffId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Kitap yok"));
        if (book.getStockQuantity() <= 0) throw new RuntimeException("Stok bitti");

        book.setStockQuantity(book.getStockQuantity() - 1);
        bookRepository.save(book);

        Loan loan = new Loan();
        loan.setBook(book);
        loan.setMember(memberRepository.findById(memberId).orElseThrow());
        loan.setStaff(staffRepository.findById(staffId != null ? staffId : 1L).orElseThrow());

        // --- DEĞİŞİKLİK BURADA ---
        // Kitabı şu an veriyoruz
        loan.setLoanDate(LocalDateTime.now());

        // Teslim süresi: 1 DAKİKA (Sunum için)
        loan.setDueDate(LocalDateTime.now().plusMinutes(1));
        // -------------------------

        return loanRepository.save(loan);
    }

    public void returnBook(Long loanId) {
        Loan loan = loanRepository.findById(loanId).orElseThrow();
        if (loan.getReturnDate() != null) throw new RuntimeException("Zaten iade edilmiş");

        // İade tarihini de şu anki saat/dakika yapıyoruz
        loan.setReturnDate(LocalDateTime.now());

        Book book = loan.getBook();
        book.setStockQuantity(book.getStockQuantity() + 1);
        bookRepository.save(book);

        // Ceza Hesabı (Dakika Bazlı)
        if (loan.getReturnDate().isAfter(loan.getDueDate())) {
            // Kaç DAKİKA gecikti?
            long minutesOverdue = ChronoUnit.MINUTES.between(loan.getDueDate(), loan.getReturnDate());

            Fine fine = new Fine();
            fine.setLoan(loan);

            // Dakika başı 0.5 TL ceza (BigDecimal formatında)
            fine.setFineAmount(BigDecimal.valueOf(minutesOverdue * 0.5));

            fine.setFineDate(LocalDate.now()); // Ceza tarihi gün olarak kalabilir
            fine.setStatus("ODENMEDI");
            fineRepository.save(fine);
        }
        loanRepository.save(loan);
    }
}