package com.writenow.write.Projections;


import com.writenow.write.Models.ModeratorStatus;

public interface WriterProjection {

    public long getId();
    public String getFull_name();
    public String getEmail();
    public int getStatus();
    public String getUrl();

}
