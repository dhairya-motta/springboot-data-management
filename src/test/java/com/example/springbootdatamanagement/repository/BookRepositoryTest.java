package com.example.springbootdatamanagement.repository;

import com.example.springbootdatamanagement.model.Author;
import com.example.springbootdatamanagement.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    void testFindAllBooksWithAuthors() {
        Author author = new Author("Test Author", "Testland");
        author = authorRepository.save(author);

        Book book = new Book("Test Book", 2023, author);
        bookRepository.save(book);

        List<Book> books = bookRepository.findAllBooksWithAuthors();
        assertFalse(books.isEmpty());
        assertNotNull(books.get(0).getAuthor());
        assertEquals("Test Author", books.get(0).getAuthor().getName());
    }
}
