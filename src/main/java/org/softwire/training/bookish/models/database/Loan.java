package org.softwire.training.bookish.models.database;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Loan {
    private int id;
    private Date issueDate;
    private Date returnDate;
    private String isbn;
    private String title;
    private String author;
    private int memberId;
    private String firstName;
    private String secondName;
    private String status;
}
