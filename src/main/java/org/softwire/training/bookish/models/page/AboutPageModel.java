package org.softwire.training.bookish.models.page;

import lombok.Getter;
import lombok.Setter;
import org.softwire.training.bookish.models.database.Technology;

import java.util.List;

@Getter
@Setter
public class AboutPageModel {
    private List<Technology> technologies;
}
