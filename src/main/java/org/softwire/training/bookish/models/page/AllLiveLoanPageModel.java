package org.softwire.training.bookish.models.page;

import lombok.Getter;
import lombok.Setter;
import org.softwire.training.bookish.models.database.Loan;

import java.util.List;

@Getter
@Setter
public class AllLiveLoanPageModel {
    private List<Loan> loans;
}
