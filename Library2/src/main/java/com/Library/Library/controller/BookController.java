package com.Library.Library.controller;

import com.Library.Library.entity.Book;
import com.Library.Library.service.BookService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getBooks() {
        return bookService.tumKitaplariGetir();
    }

    @PostMapping
    public Book addBook(@RequestBody Book book) {
        return bookService.kitapEkle(book);
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.kitapSil(id);
        return "Kitap başarıyla silindi. ID: " + id;
    }
    @PutMapping("/{id}/stock")
    public void updateStock(@PathVariable Long id, @RequestParam int amount) {
        bookService.stokGuncelle(id, amount);
    }
}