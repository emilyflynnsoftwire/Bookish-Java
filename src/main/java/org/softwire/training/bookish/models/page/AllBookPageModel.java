package org.softwire.training.bookish.models.page;

import lombok.Getter;
import lombok.Setter;
import org.softwire.training.bookish.models.database.Book;

import java.util.List;

@Getter
@Setter
public class AllBookPageModel {
    private List<Book> books;
}
