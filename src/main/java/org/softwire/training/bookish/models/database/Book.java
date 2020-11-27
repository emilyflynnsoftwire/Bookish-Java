package org.softwire.training.bookish.models.database;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Book {
    private String isbn;
    private String title;
    private String author;
    private int numberOfCopies;

    public void display() {
        System.out.println(title + " by " + author);
    }
}
