package org.softwire.training.bookish.models.database;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Loan {
    String title;
    String isbn;
    int copyId;
    Date returnDate;
    Date issueDate;
}