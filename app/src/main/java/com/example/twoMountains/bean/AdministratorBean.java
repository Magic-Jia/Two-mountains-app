package com.example.twoMountains.bean;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class AdministratorBean extends UserBean implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String account;
    public String password;
    public String name;
    public int age;
    public String email;
    public int phone;
    public String iconPath;
    public String address;


    @Override
    public String toString() {
        return "UserBean{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
