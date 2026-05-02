package com.example.springbootdatamanagement.service;

import com.example.springbootdatamanagement.model.Author;
import com.example.springbootdatamanagement.model.Book;
import com.example.springbootdatamanagement.repository.AuthorRepository;
import com.example.springbootdatamanagement.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LibraryServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private LibraryService libraryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllBooksWithAuthors() {
        Author author = new Author("Test Author", "Test");
        Book book1 = new Book("Book 1", 2020, author);
        Book book2 = new Book("Book 2", 2021, author);

        when(bookRepository.findAllBooksWithAuthors()).thenReturn(Arrays.asList(book1, book2));

        List<Book> result = libraryService.getAllBooksWithAuthors();
        assertEquals(2, result.size());
        verify(bookRepository, times(1)).findAllBooksWithAuthors();
    }

    @Test
    void testSaveBook() {
        Author author = new Author("Test Author", "Test");
        Book bookToSave = new Book("New Book", 2022, author);
        Book savedBook = new Book("New Book", 2022, author);
        savedBook.setId(1L);

        when(bookRepository.save(bookToSave)).thenReturn(savedBook);

        Book result = libraryService.saveBook(bookToSave);
        assertNotNull(result.getId());
        assertEquals("New Book", result.getTitle());
    }

    @Test
    void testUpdateBook() {
        Author author = new Author("Test Author", "Test");
        Book existingBook = new Book("Old Title", 2000, author);
        existingBook.setId(1L);

        Book updatedInfo = new Book("New Title", 2023, author);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(existingBook));
        when(bookRepository.save(any(Book.class))).thenReturn(existingBook);

        Book result = libraryService.updateBook(1L, updatedInfo);
        assertEquals("New Title", result.getTitle());
        assertEquals(2023, result.getPublicationYear());
        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).save(existingBook);
    }
}
