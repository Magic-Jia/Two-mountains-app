package com.example.twoMountains.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.twoMountains.bean.UserBean;

import java.util.List;

@Dao
public interface UserDao {
    @Insert(entity = UserBean.class)
    void registerUser(UserBean user);

    @Query("select * from UserBean")
    List<UserBean> loadAll();

    @Query("select * from UserBean where account = :account")
    UserBean queryUserByAccount(String account);

    @Query("select * from UserBean where id = :id")
    UserBean queryUserById(int id);

    @Update
    void updateUser(UserBean user);

    @Query("delete from UserBean where id = :id")
    void deleteUserById(int id);
}
