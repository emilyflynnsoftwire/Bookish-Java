package org.softwire.training.bookish.models.page;

import lombok.Getter;
import lombok.Setter;
import org.softwire.training.bookish.models.database.Member;

import java.util.List;

@Getter
@Setter
public class AllMemberPageModel {
    private List<Member> members;
}
