package org.softwire.training.bookish.services;

import org.softwire.training.bookish.models.database.Book;

import java.util.List;

public class BookService extends DatabaseService{
    public List<Book> getAllBooks() {
        String joinQuery = "SELECT book.isbn, book.title,\n" +
                "GROUP_CONCAT(author.name) AS \"authors\" FROM book\n" +
                "LEFT JOIN authorbook ON authorbook.book_isbn = book.isbn\n" +
                "LEFT JOIN author ON author.id = authorbook.author_id\n" +
                "GROUP BY book.isbn";
        return jdbi.withHandle(handle ->
                handle.createQuery(joinQuery)
                        .mapToBean(Book.class)
                        .list()
        );
    }

    public void addBook(Book book) {
        jdbi.useHandle(handle ->
                handle.createUpdate("INSERT INTO book (isbn, title) VALUES (:isbn, :title)")
                        .bind("isbn", book.getIsbn())
                        .bind("title", book.getTitle())
                        .execute()
        );
    }

    public void editBook(Book book) {
        jdbi.useHandle(handle ->
                handle.createUpdate("UPDATE book Set title=:title WHERE isbn=:isbn")
                        .bind("isbn", book.getIsbn())
                        .bind("title", book.getTitle())
                        .execute()
        );
    }
}
