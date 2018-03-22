package com.hhit.jack.jem;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 19604 on 2/21/2018.
 */

public class ActivityCollector {
    private static List<Activity> activities = new ArrayList<>();
    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public static void finishAll() {    //退出所有Activity
        for (Activity activity: activities
             ) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
