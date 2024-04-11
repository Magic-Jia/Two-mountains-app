package com.example.ble.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.ble.App;
import com.example.ble.db.dao.HelpTopicsDao;
import com.example.ble.db.dao.UserDao;


public class DBCreator {
    private static MyDB db;
    public static void init() {
        if (db == null) {
            db = Room.databaseBuilder(App.getContext(),
                    MyDB.class,
                    "twoMountains_db")
                    .allowMainThreadQueries()
                    .build();
        }
    }

    public static synchronized MyDB getInstance(Context context) {
        if (db == null) {
            db = Room.databaseBuilder(context.getApplicationContext(),
                            MyDB.class, "twoMountains_db")
                    .addMigrations(MIGRATION_1_2)
                    .build();
        }
        return db;
    }
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // 创建新表
            database.execSQL("CREATE TABLE new_chatbean (messageId INTEGER PRIMARY KEY AUTOINCREMENT, message TEXT, sendId INTEGER, sendIconPath TEXT, messageTime TEXT, isLeft INTEGER, catchId INTEGER, catchIconPath TEXT)");
            // 迁移旧表数据到新表
            database.execSQL("INSERT INTO new_chatbean (messageId, message, sendId, sendIconPath, messageTime, isLeft, catchId, catchIconPath) SELECT messageId, message, sendId, sendIconPath, messageTime, (CASE WHEN isLeft THEN 1 ELSE 0 END), catchId, catchIconPath FROM ChatBean");
            // 删除旧表
            database.execSQL("DROP TABLE ChatBean");
            // 重命名新表为旧表名称
            database.execSQL("ALTER TABLE new_chatbean RENAME TO ChatBean");
        }
    };

    public static UserDao getUserDao() {
        return db.getUserDao();
    }

    public static HelpTopicsDao getHelpTopicsDao() { return db.getHelpTopicsDao(); }
}
