package com.example.springbootdatamanagement.controller;

import com.example.springbootdatamanagement.model.Author;
import com.example.springbootdatamanagement.model.Book;
import com.example.springbootdatamanagement.service.LibraryService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    private final LibraryService libraryService;

    public BookController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping
    public String listBooks(Model model) {
        List<Book> books = libraryService.getAllBooksWithAuthors();
        model.addAttribute("books", books);
        return "book-list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("authors", libraryService.getAllAuthors());
        return "book-form";
    }

    @PostMapping("/add")
    public String addBook(@ModelAttribute Book book, RedirectAttributes redirectAttributes) {
        try {
            libraryService.saveBook(book);
            redirectAttributes.addFlashAttribute("successMessage", "Book added successfully!");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error: Database constraint violation mapping author!");
            return "redirect:/books/add";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error adding book: " + e.getMessage());
            return "redirect:/books/add";
        }
        return "redirect:/books";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Book book = libraryService.getBookById(id).orElse(null);
        if (book == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Book not found!");
            return "redirect:/books";
        }
        model.addAttribute("book", book);
        model.addAttribute("authors", libraryService.getAllAuthors());
        return "book-form";
    }

    @PostMapping("/edit/{id}")
    public String updateBook(@PathVariable("id") Long id, @ModelAttribute Book book, RedirectAttributes redirectAttributes) {
        try {
            libraryService.updateBook(id, book);
            redirectAttributes.addFlashAttribute("successMessage", "Book updated successfully!");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error: Database constraint violation!");
            return "redirect:/books/edit/" + id;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating book: " + e.getMessage());
            return "redirect:/books/edit/" + id;
        }
        return "redirect:/books";
    }
}
