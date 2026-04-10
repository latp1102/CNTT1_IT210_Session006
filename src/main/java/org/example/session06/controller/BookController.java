package org.example.session06.controller;

import org.example.session06.model.Book;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class BookController {
    private List<Book> books = List.of(
            new Book(1,"Tác giả 1", "Sách 1", 100000),
            new Book(2,"Tác giả 2", "Sách 2", 200000),
            new Book(3,"Tác giả 3", "Sách 3", 300000),
            new Book(4, "Tác giả 4", "Sách 4", 400000)
    );
    @GetMapping("/books")
    public String listBooks(Model model) {
        model.addAttribute("books", books);
        model.addAttribute("title", "danh sách sách");
        return "layout/books";
    }
    @GetMapping("/books/{id}")
    public String detailBook(@PathVariable("id") int id, Model model){
        Book book = books.stream().filter(b -> b.getId() == id).findFirst().orElse(null);
        model.addAttribute("book", book);
        model.addAttribute("title", "Chi tiết sách");
        return "layout/book-detail";
    }
}
