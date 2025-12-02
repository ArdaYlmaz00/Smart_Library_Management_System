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
}