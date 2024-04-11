package com.example.ble;

import android.app.Application;
import android.content.Context;

import com.example.ble.bean.HelpTopicsBean;
import com.example.ble.bean.UserBean;
import com.example.ble.db.DBCreator;
import com.example.ble.util.PreferenceUtil;

public class App extends Application {
    private static Context context;
    public static UserBean user;

    public void onCreate() {
        super.onCreate();
        context = this;
        DBCreator.init();
    }

    public static Context getContext() {
        return context;
    }

    public static boolean isLogin() {
        return user != null;
    }

    public static void logout() {
        PreferenceUtil.getInstance().remove("logger");
        user = null;
    }

    private void insertHelpTopics1() {
        HelpTopicsBean helpBean = new HelpTopicsBean();
        helpBean.helpTopicsId = 1;
        helpBean.title = "Aenean imperdiet Etiam ultricies nisi vel augue.";
        helpBean.helpContent = "Lorerm ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula egetdolor.Aenean massa.Cum sociis natoque penatibus et magnis dis parturient montes,nascetur ridiculus mus. Donec quam felis, utricies nec, pellentesque eu, pretium quis,sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec,vulputate eget, arcu. ln enim justo, rhoncus ut, irmperdiet a, venenatis vitae, justo.\n" +
                "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula egetdolor.Aenean massa.Cum sociis natoque penatibus et magnis dis parturient montes,nascetur ridiculus mus. Donec quam felis ultricies nec, pellentesque eu, pretium quis,sem.Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec,vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo.\n";
        DBCreator.getHelpTopicsDao().insert(helpBean);
    }
}
