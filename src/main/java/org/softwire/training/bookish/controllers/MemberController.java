package org.softwire.training.bookish.controllers;


import org.softwire.training.bookish.models.database.Member;
import org.softwire.training.bookish.models.page.AllMemberPageModel;
import org.softwire.training.bookish.models.page.MemberPageModel;
import org.softwire.training.bookish.services.MemberService;
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
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @RequestMapping("")
    ModelAndView members() {

        List<Member> allMembers = memberService.getAllMembers();

        AllMemberPageModel memberPageModel = new AllMemberPageModel();
        memberPageModel.setMembers(allMembers);

        return new ModelAndView("members", "model", memberPageModel);
    }

    @RequestMapping("/{id}")
    ModelAndView singleMember(@PathVariable("id") int id) {

        Member member = memberService.getMember(id);

        MemberPageModel memberPageModel = new MemberPageModel();
        memberPageModel.setMember(member);

        return new ModelAndView("member", "model", memberPageModel);
    }

    @RequestMapping("/new-member")
    ModelAndView newMember() {
        return new ModelAndView("newMember");
    }

    @RequestMapping("/add-member")
    RedirectView addMember(@ModelAttribute Member member) {
        memberService.addMember(member);
        return new RedirectView("/members");
    }

    @RequestMapping("/edit-member")
    RedirectView editMember(@ModelAttribute Member member) {
        memberService.editMember(member);
        return new RedirectView("/members/" + member.getId());
    }

}

