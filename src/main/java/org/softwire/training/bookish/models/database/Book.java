package org.softwire.training.bookish.models.database;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Book {
    private String isbn;
    private String title;
    private String author;
    private int numberOfCopies;
    private List<Loan> loans;

    public void display() {
        System.out.println(title + " by " + author);
    }
}
