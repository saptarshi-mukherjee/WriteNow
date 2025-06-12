package com.writenow.write.DTO.ResponseDto;

public class WriterResponseDto {
    private Long id;
    private String fulName;
    private String email;
    private String emailStatus;
    private String url;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFulName() {
        return fulName;
    }

    public void setFulName(String fulName) {
        this.fulName = fulName;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
