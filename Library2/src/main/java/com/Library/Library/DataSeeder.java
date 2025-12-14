package com.Library.Library;

import com.Library.Library.entity.Book;
import com.Library.Library.entity.Member;
import com.Library.Library.repository.BookRepository;
import com.Library.Library.repository.MemberRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component
public class DataSeeder implements CommandLineRunner {

    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    public DataSeeder(BookRepository bookRepository, MemberRepository memberRepository) {
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if (bookRepository.count() == 0) {
            System.out.println("--- KÜTÜPHANE ARŞİVİ YÜKLENİYOR... ---");

            kitapEkle("Kürk Mantolu Madonna", 160, 25, "1943-01-01", "https://img.iskultur.com.tr/webp/2022/04/Kurk-Mantolu-Madonna-Sert-Kapak.png");
            kitapEkle("İnce Memed 1", 436, 15, "1955-01-01", "https://upload.wikimedia.org/wikipedia/tr/thumb/6/69/InceMemed.jpg/250px-InceMemed.jpg");
            kitapEkle("Saatleri Ayarlama Enstitüsü", 382, 20, "1961-01-01", "https://img.kitapyurdu.com/v1/getImage/fn:11964184/wh:true/wi:800");
            kitapEkle("Tutunamayanlar", 724, 10, "1972-01-01", "https://i.dr.com.tr/cache/600x600-0/originals/0000000061424-1.jpg");
            kitapEkle("Çalıkuşu", 544, 30, "1922-01-01", "https://i.dr.com.tr/cache/600x600-0/originals/0000000052566-1.jpg");
            kitapEkle("Aşk-ı Memnu", 380, 22, "1900-01-01", "https://i.dr.com.tr/cache/600x600-0/originals/0001858060001-1.jpg");
            kitapEkle("Eylül", 280, 18, "1901-01-01", "https://img.kitapyurdu.com/v1/getImage/fn:11223545/wh:true/wi:500");
            kitapEkle("Kuyucaklı Yusuf", 220, 25, "1937-01-01", "https://i.dr.com.tr/cache/500x400-0/originals/0001851635001-1.jpg");
            kitapEkle("Sinekli Bakkal", 412, 16, "1936-01-01", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSa8Hjjwo0mnNzESCH5IkfxkApbHiGlr1JzZw&s");
            kitapEkle("Nutuk", 599, 100, "1927-10-20", "https://i.dr.com.tr/cache/600x600-0/originals/0001876564001-1.jpg");

            kitapEkle("Suç ve Ceza", 687, 15, "1866-01-01", "https://i.dr.com.tr/cache/600x600-0/originals/0001788076001-1.jpg");
            kitapEkle("Sefiller", 1724, 8, "1862-01-01", "https://img.kitapyurdu.com/v1/getImage/fn:1407986/wh:true/wi:500");
            kitapEkle("Anna Karenina", 864, 12, "1877-01-01", "https://upload.wikimedia.org/wikipedia/tr/f/fa/Anna_Karenina_%28film%2C_2012%29_-_afi%C5%9F.jpg");
            kitapEkle("Vadideki Zambak", 320, 20, "1835-01-01", "https://i.dr.com.tr/cache/500x400-0/originals/0000000716154-1.jpg");
            kitapEkle("Beyaz Diş", 250, 35, "1906-01-01", "https://i.dr.com.tr/cache/600x600-0/originals/0000000347422-1.jpg");
            kitapEkle("Dönüşüm", 104, 40, "1915-01-01", "https://i.dr.com.tr/cache/600x600-0/originals/0000000561966-1.jpg");
            kitapEkle("Satranç", 84, 50, "1942-01-01", "https://tamadres.com/media/catalog/product/cache/31b4a1f5d2b4098c5d22f22e695ff788/2/1/212168_9786059491402.gif");
            kitapEkle("Fareler ve İnsanlar", 128, 45, "1937-01-01", "https://i.dr.com.tr/cache/600x600-0/originals/0000000411500-1.jpg");
            kitapEkle("Küçük Prens", 96, 60, "1943-04-06", "https://upload.wikimedia.org/wikipedia/tr/thumb/f/f5/Kucukprens.jpg/960px-Kucukprens.jpg");

            kitapEkle("Harry Potter ve Felsefe Taşı", 274, 50, "1997-06-26", "https://i.dr.com.tr/cache/600x600-0/originals/0000000105599-1.jpg");
            kitapEkle("Yüzüklerin Efendisi: Yüzük Kardeşliği", 526, 40, "1954-07-29", "https://upload.wikimedia.org/wikipedia/tr/thumb/e/e6/Y%C3%BCz%C3%BCklerinEfendisi%27Y%C3%BCz%C3%BCkKarde%C5%9Fli%C4%9Fi-film.jpg/250px-Y%C3%BCz%C3%BCklerinEfendisi%27Y%C3%BCz%C3%BCkKarde%C5%9Fli%C4%9Fi-film.jpg");
            kitapEkle("Hobbit", 310, 35, "1937-09-21", "https://upload.wikimedia.org/wikipedia/tr/7/71/Hobbit_Kapak.jpg");
            kitapEkle("1984", 352, 55, "1949-06-08", "https://i.dr.com.tr/cache/600x600-0/originals/0000000064038-1.jpg");
            kitapEkle("Hayvan Çiftliği", 152, 60, "1945-08-17", "https://i.dr.com.tr/cache/600x600-0/originals/0000000105409-1.jpg");
            kitapEkle("Fahrenheit 451", 208, 25, "1953-10-19", "https://img.kitapyurdu.com/v1/getImage/fn:11643403/wh:true/miw:200/mih:200");
            kitapEkle("Dune", 712, 15, "1965-08-01", "https://i.dr.com.tr/cache/600x600-0/originals/0000000662978-1.jpg");
            kitapEkle("Simyacı", 188, 70, "1988-01-01", "https://i.dr.com.tr/cache/600x600-0/originals/0000000064552-1.jpg");
            kitapEkle("Şeker Portakalı", 182, 55, "1968-01-01", "https://i.dr.com.tr/cache/600x600-0/originals/0000000064031-1.jpg");
            kitapEkle("Uçurtma Avcısı", 375, 20, "2003-05-29", "https://i.dr.com.tr/cache/600x600-0/originals/0000000153127-1.jpg");
            kitapEkle("Da Vinci Şifresi", 520, 18, "2003-03-18", "https://i.dr.com.tr/cache/600x600-0/originals/0000000142987-1.jpg");
            kitapEkle("Sherlock Holmes - Akıl Oyunları", 420, 12, "1892-10-14", "https://img.kitapyurdu.com/v1/getImage/fn:1008839/wh:true/wi:500");
            kitapEkle("Olasılıksız", 470, 15, "2005-01-01", "https://i.dr.com.tr/cache/500x400-0/originals/0000000204878-1.jpg");

            System.out.println("--- 32 ADET KİTAP BAŞARIYLA YÜKLENDİ ---");
        }

        uyeEkle("ahmet@mail.com", "1234", "Ahmet", "Yılmaz", "MEMBER");
        uyeEkle("admin@mail.com", "1234", "Yönetici", "Admin", "ADMIN");
    }
    private void kitapEkle(String title, int page, int stock, String date, String imgUrl) {
        Book book = new Book();
        book.setTitle(title);
        book.setPageCount(page);
        book.setStockQuantity(stock);
        book.setPublicationDate(LocalDate.parse(date));
        book.setImageUrl(imgUrl);
        bookRepository.save(book);
    }

    private void uyeEkle(String email, String pass, String name, String surname, String role) {
        if (memberRepository.findByEmail(email) == null) {
            Member m = new Member();
            m.setEmail(email);
            m.setPassword(pass);
            m.setFirstName(name);
            m.setLastName(surname);
            m.setRole(role);
            memberRepository.save(m);
        }
    }
}