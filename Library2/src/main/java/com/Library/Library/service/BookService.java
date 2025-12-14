package com.Library.Library.service;

import com.Library.Library.entity.Book;
import com.Library.Library.repository.BookRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> tumKitaplariGetir() {
        return bookRepository.findAll();
    }

    public Book kitapEkle(Book book) {
        return bookRepository.save(book);
    }
    public void kitapSil(Long id) {
        bookRepository.deleteById(id);
    }
    public void stokGuncelle(Long id, int miktar) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kitap bulunamadı"));

        int yeniStok = book.getStockQuantity() + miktar;
        if (yeniStok < 0) yeniStok = 0;

        book.setStockQuantity(yeniStok);
        bookRepository.save(book);
    }
}