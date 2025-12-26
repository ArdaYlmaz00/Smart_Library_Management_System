package com.Library.Library.controller;

import com.Library.Library.entity.Member;
import com.Library.Library.repository.MemberRepository;
import com.Library.Library.service.EmailService; // Eklendi
import org.springframework.beans.factory.annotation.Autowired; // Eklendi
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberRepository memberRepository;

    @Autowired
    private EmailService emailService; // Şifre sıfırlama için mail servisi

    public MemberController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @GetMapping
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    @PostMapping
    public Member addMember(@RequestBody Member member) {
        return memberRepository.save(member);
    }

    @PutMapping("/update/{id}")
    public Member updateMember(@PathVariable Long id, @RequestBody Member memberRequest) {
        return memberRepository.findById(id).map(member -> {
            member.setFirstName(memberRequest.getFirstName());
            member.setLastName(memberRequest.getLastName());
            member.setEmail(memberRequest.getEmail());
            member.setPassword(memberRequest.getPassword());
            return memberRepository.save(member);
        }).orElseThrow(() -> new RuntimeException("Üye bulunamadı"));
    }

    @PutMapping("/change-password/{id}")
    public org.springframework.http.ResponseEntity<String> changePassword(@PathVariable Long id, @RequestBody java.util.Map<String, String> passwords) {
        String newPass = passwords.get("newPass");
        String confirmPass = passwords.get("confirmPass");
        String oldPass = passwords.get("oldPass");

        return memberRepository.findById(id).map(member -> {
            if (!member.getPassword().equals(oldPass)) {
                return org.springframework.http.ResponseEntity.badRequest().body("Eski şifreniz hatalı!");
            }
            if (!newPass.equals(confirmPass)) {
                return org.springframework.http.ResponseEntity.badRequest().body("Yeni şifreler birbiriyle uyuşmuyor!");
            }
            member.setPassword(newPass);
            memberRepository.save(member);
            return org.springframework.http.ResponseEntity.ok("Şifreniz başarıyla değiştirildi.");
        }).orElseThrow(() -> new RuntimeException("Üye bulunamadı"));
    }

    @PostMapping("/login")
    public org.springframework.http.ResponseEntity<?> login(@RequestBody java.util.Map<String, String> loginData) {
        String email = loginData.get("email");
        String password = loginData.get("password");

        Member member = memberRepository.findByEmail(email);

        if (member == null || !member.getPassword().equals(password)) {
            return org.springframework.http.ResponseEntity.status(401).body("Hatalı Email veya Şifre!");
        }

        String newToken = java.util.UUID.randomUUID().toString();
        member.setToken(newToken);
        memberRepository.save(member);

        return org.springframework.http.ResponseEntity.ok(member);
    }

    @PostMapping("/logout/{id}")
    public void logout(@PathVariable Long id) {
        Member member = memberRepository.findById(id).orElse(null);
        if (member != null) {
            member.setToken(null);
            memberRepository.save(member);
        }
    }

    @PostMapping("/register")
    public org.springframework.http.ResponseEntity<?> register(@RequestBody Member member) {
        if (memberRepository.findByEmail(member.getEmail()) != null) {
            return org.springframework.http.ResponseEntity.badRequest().body("HATA: Bu email adresi zaten kayıtlı!");
        }
        member.setRole("MEMBER");
        memberRepository.save(member);
        return org.springframework.http.ResponseEntity.ok("Kayıt başarılı! Şimdi giriş yapabilirsiniz.");
    }

    // ŞİFREMİ UNUTTUM - EKLENDİ
    @PostMapping("/forgot-password")
    public org.springframework.http.ResponseEntity<?> forgotPassword(@RequestBody java.util.Map<String, String> data) {
        String email = data.get("email");
        Member member = memberRepository.findByEmail(email);

        if (member == null) {
            return org.springframework.http.ResponseEntity.badRequest().body("Bu email adresiyle kayıtlı üye bulunamadı.");
        }

        // Yeni rastgele şifre: 123456 (Basitlik olsun diye sabit yaptım)
        String newPassword = "123456";

        member.setPassword(newPassword);
        memberRepository.save(member);

        emailService.sendSimpleEmail(
                email,
                "Şifre Sıfırlama Talebi",
                "Merhaba " + member.getFirstName() + ",\n\n" +
                        "Yeni şifreniz: " + newPassword + "\n\n" +
                        "Giriş yaptıktan sonra değiştirmeyi unutmayın."
        );

        return org.springframework.http.ResponseEntity.ok("Yeni şifreniz e-posta adresinize gönderildi!");
    }
}