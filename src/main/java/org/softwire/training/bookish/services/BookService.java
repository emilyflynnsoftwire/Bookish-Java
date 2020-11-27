package org.softwire.training.bookish.services;

import org.softwire.training.bookish.models.database.Book;
import org.softwire.training.bookish.models.database.Loan;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService extends DatabaseService {
    public List<Book> getAllBooks() {
        String query = "SELECT book.isbn, title, author, numberOfCopies, COUNT(status) AS copiesOut FROM book\n" +
                "LEFT JOIN loan ON book.isbn = loan.book_isbn AND status='ACTIVE'\n" +
                "GROUP BY book.isbn ORDER BY book.title";
        return jdbi.withHandle(handle ->
                handle.createQuery(query)
                        .mapToBean(Book.class)
                        .list()
        );
    }

    public List<Book> searchBooks(String search) {
        String query = "SELECT book.isbn, title, author, numberOfCopies, COUNT(status) AS copiesOut FROM book\n" +
                "LEFT JOIN loan ON book.isbn = loan.book_isbn AND status='ACTIVE'\n" +
                "WHERE book.title LIKE :search OR book.author LIKE :search\n" +
                "GROUP BY book.isbn ORDER BY book.title";
        return jdbi.withHandle(handle ->
                handle.createQuery(query)
                        .bind("search", "%" + search + "%")
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
                        "SET title=:title, author=:author, numberOfCopies=:numberOfCopies WHERE isbn=:isbn")
                        .bind("isbn", book.getIsbn())
                        .bind("title", book.getTitle())
                        .bind("author", book.getAuthor())
                        .bind("numberOfCopies", book.getNumberOfCopies())
                        .execute()
        );
    }

    public Book getBook(String isbn) {
            String query = "SELECT book.isbn, book.title, book.author, book.numberOfCopies FROM book WHERE isbn = :isbn";
            Book book = jdbi.withHandle(handle ->
                    handle.createQuery(query)
                          .bind("isbn", isbn)
                          .mapToBean(Book.class)
                          .list().get(0)
            );
            String activeLoansQuery = "SELECT return_date, member.firstName AS firstName, member.secondName AS secondName" +
                    " FROM loan INNER JOIN member ON member.id = loan.member_id WHERE book_isbn = :isbn AND status = 'Active'";
            List<Loan> loans = jdbi.withHandle(handle ->
                    handle.createQuery(activeLoansQuery)
                          .bind("isbn", isbn)
                          .mapToBean(Loan.class)
                          .list()
            );
            book.setLoans(loans);
            return book;
    }
}
