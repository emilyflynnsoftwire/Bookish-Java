package org.softwire.training.bookish.services;

import org.softwire.training.bookish.models.database.Book;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService extends DatabaseService{
    public List<Book> getAllBooks() {
        String query = "SELECT isbn, title, author, numberOfCopies FROM book\n";
        return jdbi.withHandle(handle ->
                handle.createQuery(query)
                        .mapToBean(Book.class)
                        .list()
        );
    }

    public void addBook(Book book) {
        jdbi.useHandle(handle ->
                handle.createUpdate("INSERT INTO book (isbn, title, author, numberOfCopies) " +
                        "VALUES (:isbn, :title, :author, :numberOfCopies)")
                        .bind("isbn", book.getIsbn())
                        .bind("title", book.getTitle())
                        .bind("author", book.getAuthor())
                        .bind("numberOfCopies", book.getNumberOfCopies())
                        .execute()
        );
    }

    public void editBook(Book book) {
        jdbi.useHandle(handle ->
                handle.createUpdate("UPDATE book " +
                        "Set title=:title, author=:author, numberOfCopies=:numberOfCopies WHERE isbn=:isbn")
                        .bind("isbn", book.getIsbn())
                        .bind("title", book.getTitle())
                        .bind("author", book.getAuthor())
                        .bind("numberOfCopies", book.getNumberOfCopies())
                        .execute()
        );
    }

    public Book getBook(String isbn) {
            String query = "SELECT book.isbn, book.title, book.author, book.numberOfCopies FROM book WHERE isbn = :isbn";
            return jdbi.withHandle(handle ->
                    handle.createQuery(query)
                          .bind("isbn", isbn)
                          .mapToBean(Book.class)
                          .list().get(0)
            );
    }
}
