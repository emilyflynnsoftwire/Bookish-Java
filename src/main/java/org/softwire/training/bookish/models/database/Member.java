package org.softwire.training.bookish.models.database;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Member {
    private int id;
    private String firstName;
    private String secondName;
    private String emailAddress;
    private String telephoneNumber;

    public String getFullName() {
        return firstName + " " + secondName;
    }
}
