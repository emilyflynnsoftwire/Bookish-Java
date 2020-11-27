package org.softwire.training.bookish.controllers;


import org.softwire.training.bookish.models.database.Book;
import org.softwire.training.bookish.models.page.AllBookPageModel;
import org.softwire.training.bookish.models.page.BookPageModel;
import org.softwire.training.bookish.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @RequestMapping("")
    ModelAndView books() {

        List<Book> allBooks = bookService.getAllBooks();

        AllBookPageModel pageModel= new AllBookPageModel();
        pageModel.setBooks(allBooks);

        return new ModelAndView("books", "model", pageModel);
    }

    @RequestMapping("/{isbn}")
    ModelAndView singleBook(@PathVariable("isbn") String isbn) {

        Book book = bookService.getBook(isbn);

        BookPageModel model = new BookPageModel();
        model.setBook(book);

        return new ModelAndView("book", "model", model);
    }

    @RequestMapping("/new-book")
    ModelAndView newBook() {
        return new ModelAndView("newBook");
    }

    @RequestMapping("/add-book")
    RedirectView addBook(@ModelAttribute Book book) {
        bookService.addBook(book);
        return new RedirectView("/books");
    }

    @RequestMapping("/edit-book")
    RedirectView editBook(@ModelAttribute Book book) {
        bookService.editBook(book);
        return new RedirectView("/books/" + book.getIsbn());
    }

}
