package org.softwire.training.bookish.controllers;


import org.softwire.training.bookish.models.database.Loan;
import org.softwire.training.bookish.models.page.AllLiveLoanPageModel;
import org.softwire.training.bookish.models.page.LoanPageModel;
import org.softwire.training.bookish.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequestMapping("/loans")
public class LoanController {
    private final LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @RequestMapping("")
    ModelAndView liveLoans() {
        List<Loan> allLiveLoans = loanService.getAllLiveLoans();

        AllLiveLoanPageModel allLiveLoanPageModel = new AllLiveLoanPageModel();
        allLiveLoanPageModel.setLoans(allLiveLoans);

        return new ModelAndView("loans", "model", allLiveLoanPageModel);
    }

    @RequestMapping("/{id}")
    ModelAndView singleLoan(@PathVariable("id") int id) {
        Loan loan = loanService.getLoan(id);

        LoanPageModel loanPageModel = new LoanPageModel();
        loanPageModel.setLoan(loan);

        return new ModelAndView("loan", "model", loanPageModel);
    }

    @RequestMapping("/new-loan")
    ModelAndView newLoan() {
        return new ModelAndView("newLoan");
    }

    @RequestMapping("/add-loan")
    RedirectView addLoan(@ModelAttribute Loan loan) {
        loanService.addLoan(loan);
        return new RedirectView("/loans");
    }

    @RequestMapping("/renew-loan/{id}")
    RedirectView renewLoan(@PathVariable("id") int id) {
        loanService.renewLoan(id);
        return new RedirectView("/loans/" + id);
    }
}
