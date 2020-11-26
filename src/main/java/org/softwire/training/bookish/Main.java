package org.softwire.training.bookish;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.JoinRowMapper;
import org.jdbi.v3.core.mapper.reflect.BeanMapper;
import org.jdbi.v3.core.mapper.reflect.ConstructorMapper;
import org.jdbi.v3.core.result.RowView;
import org.softwire.training.bookish.models.database.Author;
import org.softwire.training.bookish.models.database.Book;

import java.sql.*;
import java.util.List;
import java.util.Map;


public class Main {

    public static void main(String[] args) throws SQLException {
        String hostname = "localhost";
        String database = "bookish";
        String user = "root";
        String password = System.getenv("BOOKISH_KEY");
        String connectionString = "jdbc:mysql://" + hostname + "/" + database + "?user=" + user + "&password=" + password + "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT&useSSL=false";

        jdbcMethod(connectionString);
        jdbiMethod(connectionString);
    }

    private static void jdbcMethod(String connectionString) throws SQLException {
        System.out.println("JDBC method...");
        Connection connection = DriverManager.getConnection(connectionString);
        String query = "SELECT * FROM book ORDER BY title ASC";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                System.out.println(rs.getString("title"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void jdbiMethod(String connectionString) {
        System.out.println("\nJDBI method...");
        Jdbi jdbi = Jdbi.create(connectionString);
        String joinQuery = "SELECT book.isbn, book.title,\n" +
                "GROUP_CONCAT(author.name) AS \"authors\" FROM book\n" +
                "LEFT JOIN authorbook ON authorbook.book_isbn = book.isbn\n" +
                "LEFT JOIN author ON author.id = authorbook.author_id\n" +
                "GROUP BY book.isbn";
        List<Book> books = jdbi.withHandle(handle ->
                    handle.createQuery(joinQuery)
                          .mapToBean(Book.class)
                          .list());
        books.forEach(Book::display);
    }
}
