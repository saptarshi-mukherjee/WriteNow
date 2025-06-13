package com.writenow.write.Models;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "moderators")
@DiscriminatorValue("MODERATOR")
public class Moderator extends User {
    private Long contestCount;
    private ModeratorStatus moderatorStatus;
    @OneToMany(mappedBy = "createdBy")
    @JsonManagedReference("contest-moderator")
    private List<Contest> contests;


    public Moderator() {
        super();
        contestCount=0L;
        contests=new ArrayList<>();
    }


    public List<Contest> getContests() {
        return contests;
    }

    public void setContests(List<Contest> contests) {
        this.contests = contests;
    }

    public Long getContestCount() {
        return contestCount;
    }

    public void setContestCount(Long contestCount) {
        this.contestCount = contestCount;
    }

    public ModeratorStatus getModeratorStatus() {
        return moderatorStatus;
    }

    public void setModeratorStatus(ModeratorStatus moderatorStatus) {
        this.moderatorStatus = moderatorStatus;
    }
}
