package com.Library.Library.service;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public void sendLateReturnEmail(String email, String bookTitle) {
        System.out.println("📨 [E-POSTA GÖNDERİLDİ] -> Kime: " + email);
        System.out.println("   Konu: Geç İade Bildirimi");
        System.out.println("   İçerik: '" + bookTitle + "' kitabını geç iade ettiğiniz için ceza uygulandı.");
    }
}