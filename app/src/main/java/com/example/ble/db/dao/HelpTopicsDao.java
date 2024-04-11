package com.example.ble.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.ble.bean.HelpTopicsBean;

import java.util.List;

@Dao
public interface HelpTopicsDao {
    @Insert(entity = HelpTopicsBean.class)
    void registerUser(HelpTopicsBean helpTopics);

    @Insert(entity = HelpTopicsBean.class)
    void insert(HelpTopicsBean bean);

    @Query("select * from HelpTopicsBean")
    List<HelpTopicsBean> loadAll();

    @Query("select * from HelpTopicsBean where title = :title")
    HelpTopicsBean queryHelpTopicsByTitle(String title);

    @Update
    void updateHelpTopics(HelpTopicsBean helpTopics);
}
