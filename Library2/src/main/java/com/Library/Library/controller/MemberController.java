package com.Library.Library.controller;

import com.Library.Library.entity.Member;
import com.Library.Library.repository.MemberRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberRepository memberRepository;

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
}