package com.writenow.write.DTO.RequestDto;

public class ModeratorRequestDto {
    private String fullName, email, moderatorStatus;


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

    public String getModeratorStatus() {
        return moderatorStatus;
    }

    public void setModeratorStatus(String moderatorStatus) {
        this.moderatorStatus = moderatorStatus;
    }
}
