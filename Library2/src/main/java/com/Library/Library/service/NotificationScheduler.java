package com.Library.Library.service;

import com.Library.Library.entity.Loan;
import com.Library.Library.repository.LoanRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class NotificationScheduler {

    private final LoanRepository loanRepository;
    private final EmailService emailService;

    private static final double MINUTE_FINE = 0.5;

    public NotificationScheduler(LoanRepository loanRepository, EmailService emailService) {
        this.loanRepository = loanRepository;
        this.emailService = emailService;
    }

    @Scheduled(fixedRate = 30000)
    public void checkOverdueLoans() {
        List<Loan> activeLoans = loanRepository.findByReturnDateIsNull();
        LocalDateTime now = LocalDateTime.now();

        for (Loan loan : activeLoans) {
            if (loan.getDueDate().isBefore(now)) {
                long overdueMinutes = ChronoUnit.MINUTES.between(loan.getDueDate(), now);

                if (overdueMinutes > 0 && loan.getMember() != null && loan.getMember().getEmail() != null) {
                    sendOverdueNotification(loan, overdueMinutes);
                }
            }
        }
    }

    private void sendOverdueNotification(Loan loan, long overdueMinutes) {
        String subject = "GECİKME UYARISI!";
        String memberName = "Üye";
        try {
            if(loan.getMember().getFirstName() != null)
                memberName = loan.getMember().getFirstName();
        } catch(Exception e) {}

        double currentFine = overdueMinutes * MINUTE_FINE;

        String text = "Sayın " + memberName + ",\n" +
                "Kitabınızın süresi " + overdueMinutes + " dakika geçti.\n" +
                "Güncel Ceza Tutarınız: " + currentFine + " TL\n" +
                "Lütfen ödeme yapıp kitabı iade ediniz.";

        emailService.sendSimpleMessage(loan.getMember().getEmail(), subject, text);
    }
}