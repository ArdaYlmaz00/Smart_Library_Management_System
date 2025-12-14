package com.Library.Library.controller;

import com.Library.Library.entity.Member;
import com.Library.Library.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private MemberRepository memberRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Member loginRequest) {
        System.out.println("--- GİRİŞ DENEMESİ BAŞLADI ---");
        System.out.println("1. Girilen Email: " + loginRequest.getEmail());
        System.out.println("2. Girilen Şifre: " + loginRequest.getPassword());

        Optional<Member> userOpt = memberRepository.findAll().stream()
                .filter(m -> m.getEmail().equals(loginRequest.getEmail()))
                .findFirst();

        if (userOpt.isPresent()) {
            Member dbMember = userOpt.get();
            System.out.println("3. ✅ Kullanıcı Veritabanında Bulundu!");
            System.out.println("   - DB'deki ID: " + dbMember.getId());
            System.out.println("   - DB'deki Email: " + dbMember.getEmail());
            System.out.println("   - DB'deki Şifre: " + dbMember.getPassword()); // Burası null ise Entity bozuktur!
            System.out.println("   - DB'deki Rol: " + dbMember.getRole());

            if (dbMember.getPassword() != null && dbMember.getPassword().equals(loginRequest.getPassword())) {
                System.out.println("4. ✅ ŞİFRE EŞLEŞTİ! GİRİŞ BAŞARILI.");
                return ResponseEntity.ok(dbMember);
            } else {
                System.out.println("4. ❌ ŞİFRE HATALI! (Veya DB şifresi null)");
                return ResponseEntity.status(401).body("Şifre Hatalı!");
            }
        } else {
            System.out.println("3. ❌ BU EMAİL İLE KULLANICI YOK!");
            return ResponseEntity.status(401).body("Kullanıcı Bulunamadı!");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Member newMember) {
        if (memberRepository.findAll().stream().anyMatch(m -> m.getEmail().equals(newMember.getEmail()))) {
            return ResponseEntity.badRequest().body("Bu e-posta zaten kayıtlı!");
        }
        return ResponseEntity.ok(memberRepository.save(newMember));
    }
}