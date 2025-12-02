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
}