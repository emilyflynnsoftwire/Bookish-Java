package org.softwire.training.bookish.services;

import org.softwire.training.bookish.models.database.Loan;
import org.softwire.training.bookish.models.database.Member;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService extends DatabaseService {
    public List<Member> getAllMembers() {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM member")
                        .mapToBean(Member.class)
                        .list()
        );
    }

    public Member getMember(int id) {
        Member member = jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM member WHERE id = :id")
                        .bind("id", id)
                        .mapToBean(Member.class)
                        .list().get(0)
        );
        List<Loan> loans = jdbi.withHandle(handle ->
                handle.createQuery("SELECT return_date, issue_date, book.isbn AS isbn, book.title AS title" +
                        " FROM loan INNER JOIN book ON book.isbn = loan.book_isbn" +
                        " WHERE member_id = :id AND status = 'ACTIVE'")
                      .bind("id", id)
                      .mapToBean(Loan.class)
                      .list()
        );
        member.setLoans(loans);
        return member;
    }

    public void addMember(Member member) {
        jdbi.useHandle(handle ->
                handle.createUpdate("INSERT INTO member " +
                        "(firstName, secondName, telephoneNumber, emailAddress)" +
                        " VALUES (:firstName, :secondName, :telephoneNumber, :emailAddress)")
                        .bind("firstName", member.getFirstName())
                        .bind("secondName", member.getSecondName())
                        .bind("telephoneNumber", member.getTelephoneNumber())
                        .bind("emailAddress", member.getEmailAddress())
                        .execute()
        );
    }

    public void editMember(Member member) {
        jdbi.useHandle(handle ->
                handle.createUpdate("UPDATE member\n" +
                        "SET \n" +
                        "firstName = :firstName,\n" +
                        "secondName = :secondName,\n" +
                        "emailAddress = :emailAddress,\n" +
                        "telephoneNumber = :telephoneNumber\n" +
                        "WHERE id = :id")
                        .bind("firstName", member.getFirstName())
                        .bind("secondName", member.getSecondName())
                        .bind("telephoneNumber", member.getTelephoneNumber())
                        .bind("emailAddress", member.getEmailAddress())
                        .bind("id", member.getId())
                        .execute()
        );
    }
}
