package com.writenow.write.DTO.ResponseDto;

public class FollowerResponseDto {
    private long followerId;
    private String followerName;


    public long getFollowerId() {
        return followerId;
    }

    public void setFollowerId(long followerId) {
        this.followerId = followerId;
    }

    public String getFollowerName() {
        return followerName;
    }

    public void setFollowerName(String followerName) {
        this.followerName = followerName;
    }
}
