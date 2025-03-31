package com.example.demo.book;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import jakarta.validation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(path = "api/v1/book")
public class BookController {

    @Autowired
    private BookService bookService;
    
    @GetMapping
	public List<Book> getBooks() {
        return bookService.getBooks();
    }

    @PostMapping
    public void registerNewBook(@Valid @RequestBody Book book) {
        bookService.addNewBook(book);
    }

    @DeleteMapping(path = "{id}")
    public void deleteBook(
        @PathVariable("id") Long id) {
            bookService.deleteBook(id);
    }

    @PutMapping(path = "{id}")
    public void updateBook(
        @PathVariable Long id,
        @RequestParam(required = false) String title,
        @RequestParam(required = false) Long authorId,
        @RequestParam(required = false) String genre,
        @RequestParam(required = false) Number price) {
            bookService.updateBook(id, title, authorId, genre, price);
    }
    
}






