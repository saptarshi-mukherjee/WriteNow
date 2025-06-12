package com.writenow.write.Projections;

public interface ModeratorProjection {
    public long getId();
    public String getFull_name();
    public int getStatus();
    public String getEmail();
    public long getContest_count();
    public int getModerator_status();
}
