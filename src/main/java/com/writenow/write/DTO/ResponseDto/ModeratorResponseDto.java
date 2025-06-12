package com.writenow.write.DTO.ResponseDto;

public class ModeratorResponseDto {
    private long id;
    private String fullName, email, emailStatus, moderatorStatus;
    public long contestCount;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailStatus() {
        return emailStatus;
    }

    public void setEmailStatus(String emailStatus) {
        this.emailStatus = emailStatus;
    }

    public String getModeratorStatus() {
        return moderatorStatus;
    }

    public void setModeratorStatus(String moderatorStatus) {
        this.moderatorStatus = moderatorStatus;
    }

    public long getContestCount() {
        return contestCount;
    }

    public void setContestCount(long contestCount) {
        this.contestCount = contestCount;
    }
}
