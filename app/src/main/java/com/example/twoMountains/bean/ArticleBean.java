package com.example.twoMountains.bean;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;


@Entity
public class ArticleBean implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String title;
    public String content;
    public int watchQuantity = 0;
}
