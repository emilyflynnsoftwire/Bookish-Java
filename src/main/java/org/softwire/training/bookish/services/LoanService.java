package org.softwire.training.bookish.services;

import org.softwire.training.bookish.models.database.Loan;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LoanService extends DatabaseService {
    public List<Loan> getAllLiveLoans() {
        String joinQuery = "SELECT loan.id, loan.issue_date, loan.return_date,\n" +
                "loan.book_isbn, book.title, book.author,\n" +
                "loan.member_id, member.firstName, member.secondName,\n" +
                "loan.status FROM loan\n" +
                "LEFT JOIN book ON loan.book_isbn = book.isbn\n" +
                "LEFT JOIN member ON loan.member_id = member.id\n" +
                "WHERE loan.status = 'Active' OR loan.status = 'Overdue'\n" +
                "ORDER BY loan.issue_date";
        return jdbi.withHandle(handle ->
                handle.createQuery(joinQuery)
                        .mapToBean(Loan.class)
                        .list()
        );
    }

    public Loan getLoan(int id) {
        String joinQuery = "SELECT loan.id, loan.issue_date, loan.return_date,\n" +
                "loan.book_isbn, book.title, book.author,\n" +
                "loan.member_id, member.firstName, member.secondName,\n" +
                "loan.status FROM loan\n" +
                "LEFT JOIN book ON loan.book_isbn = book.isbn\n" +
                "LEFT JOIN member ON loan.member_id = member.id\n" +
                "WHERE loan.id = :id";
        return jdbi.withHandle(handle ->
                handle.createQuery(joinQuery)
                        .bind("id", id)
                        .mapToBean(Loan.class)
                        .list().get(0)
        );
    }

    public void addLoan(Loan loan) {
        LocalDate today = LocalDate.now();
        LocalDate twoWeeksFromToday = today.plusWeeks(2);
        String joinQuery = "INSERT INTO loan\n" +
                "(issue_date, return_date, book_isbn, member_id, status)\n" +
                "VALUES (:issueDate, :returnDate, :isbn, :memberId, :status)";
        jdbi.useHandle(handle ->
                handle.createUpdate(joinQuery)
                        .bind("issueDate", today)
                        .bind("returnDate", twoWeeksFromToday)
                        .bind("isbn", loan.getIsbn())
                        .bind("memberId", loan.getMemberId())
                        .bind("status", "Active")
                        .execute()
        );
    }

    public void renewLoan(int id) {
        LocalDate twoWeeksFromToday = LocalDate.now().plusWeeks(2);
        String joinQuery = "UPDATE loan SET\n" +
                "return_date = :returnDate\n" +
                "WHERE id = :id";
        jdbi.useHandle(handle ->
                handle.createUpdate(joinQuery)
                        .bind("returnDate", twoWeeksFromToday)//edit
                        .bind("id", id)
                        .execute()
        );
    }
}
