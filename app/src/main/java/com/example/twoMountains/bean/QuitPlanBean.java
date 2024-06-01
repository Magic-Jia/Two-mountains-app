package com.example.twoMountains.bean;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class QuitPlanBean {
    @PrimaryKey(autoGenerate = true)
    public int planId;
    public int userId;
    public String startDate;
    public String quitDate;
}
