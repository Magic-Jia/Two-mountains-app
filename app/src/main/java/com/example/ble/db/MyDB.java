package com.example.ble.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.ble.bean.HelpTopicsBean;
import com.example.ble.bean.UserBean;
import com.example.ble.db.dao.HelpTopicsDao;
import com.example.ble.db.dao.UserDao;


@Database(entities = {
        UserBean.class,
        HelpTopicsBean.class,
}, version = 1, exportSchema = false)
public abstract class MyDB extends RoomDatabase {
    public abstract UserDao getUserDao();

    public abstract HelpTopicsDao getHelpTopicsDao();
}
