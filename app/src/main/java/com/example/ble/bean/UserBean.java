package com.example.ble.bean;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class UserBean implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String phone;
    public String pwd;
    public String name;
    public String iconPath;


    @Override
    public String toString() {
        return "UserBean{" +
                "id=" + id +
                ", phone='" + phone + '\'' +
                ", pwd='" + pwd + '\'' +
                ", name='" + name + '\'' +
                ", isStudent=" +
                '}';
    }
}
