package com.example.twoMountains.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.twoMountains.bean.AdministratorBean;

import java.util.List;

@Dao
public interface AdministratorDao {
    @Insert(entity = AdministratorBean.class)
    void registerAdministrator(AdministratorBean administrator);

    @Query("select * from AdministratorBean")
    List<AdministratorBean> loadAll();

    @Query("select * from AdministratorBean where account = :account")
    AdministratorBean queryAdministratorByAccount(String account);

    @Update
    void updateAdministrator(AdministratorBean administrator);

    @Query("delete from AdministratorBean where id = :id")
    void deleteAdministratorById(int id);
}
