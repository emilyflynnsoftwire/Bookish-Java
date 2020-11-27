package org.softwire.training.bookish.models.database;

import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
public class Loan {
    private String title;
    private String isbn;
    private Date returnDate;
    private Date issueDate;
    private String status;
    private String borrowerFirstName;
    private String borrowerSecondName;
    private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    public String getFormattedDate() {
        return format.format(returnDate);
    }
}
