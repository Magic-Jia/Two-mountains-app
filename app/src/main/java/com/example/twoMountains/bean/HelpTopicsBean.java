package com.example.twoMountains.bean;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class HelpTopicsBean {
    @PrimaryKey(autoGenerate = true)
    public int helpTopicsId;
    public String title;
    public String helpContent;
}
