package com.example.twoMountains.bean;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CigaretteDataBean {
    @PrimaryKey(autoGenerate = true)
    public int cigaretteId;
    public int userId;
    public String date;
    public float motivation = -1;
    public int cigaretteQuantity = -1;
    public int vapeQuantity = -1;
}
