package com.example.springbootdatamanagement.service;

import com.example.springbootdatamanagement.model.Author;
import com.example.springbootdatamanagement.model.Book;
import com.example.springbootdatamanagement.repository.AuthorRepository;
import com.example.springbootdatamanagement.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LibraryService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public LibraryService(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    // --- Author Operations ---
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }

    public Optional<Author> getAuthorById(Long id) {
        return authorRepository.findById(id);
    }

    // --- Book Operations ---
    public List<Book> getAllBooksWithAuthors() {
        return bookRepository.findAllBooksWithAuthors();
    }

    @Transactional
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    @Transactional
    public Book updateBook(Long id, Book updatedBookData) {
        Book existingBook = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
        existingBook.setTitle(updatedBookData.getTitle());
        existingBook.setPublicationYear(updatedBookData.getPublicationYear());
        existingBook.setAuthor(updatedBookData.getAuthor());
        return bookRepository.save(existingBook);
    }
}
