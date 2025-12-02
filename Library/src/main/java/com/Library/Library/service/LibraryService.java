package com.Library.Library.service;

import com.Library.Library.entity.*;
import com.Library.Library.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
public class LibraryService {

    private final AuthorRepository authorRepository;
    private final MemberRepository memberRepository;
    private final StaffRepository staffRepository;
    private final LoanRepository loanRepository;
    private final FineRepository fineRepository;

    public LibraryService(AuthorRepository authorRepository, MemberRepository memberRepository,
                          StaffRepository staffRepository, LoanRepository loanRepository, FineRepository fineRepository) {
        this.authorRepository = authorRepository;
        this.memberRepository = memberRepository;
        this.staffRepository = staffRepository;
        this.loanRepository = loanRepository;
        this.fineRepository = fineRepository;
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }
    public Author addAuthor(Author author) {
        return authorRepository.save(author);
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }
    public Member addMember(Member member) {
        return memberRepository.save(member);
    }

    public List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }
    public Staff addStaff(Staff staff) {
        return staffRepository.save(staff);
    }
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }
    public Loan addLoan(Loan loan) {
        return loanRepository.save(loan);
    }


    public List<Fine> getAllFines() {
        return fineRepository.findAll();
    }

    @Transactional
    public void calculateFineForLoan(Integer loanId) {

        loanRepository.calculateFine(loanId, new BigDecimal("5.00"));
    }
}