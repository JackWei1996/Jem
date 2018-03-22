package com.hhit.jack.jem.app;

import android.app.Application;

import com.hhit.jack.jem.db.User;

import org.xutils.x;

/**
 * Created by 19604 on 3/15/2018.
 */

public class MyApplication extends Application {

    private User user;

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

