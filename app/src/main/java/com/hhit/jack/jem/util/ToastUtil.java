package com.hhit.jack.jem.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by 19604 on 3/15/2018.
 */

public class ToastUtil {
    private static Toast toast;

    public static void showToast(Context context, String content) {
        if(toast == null) {
            toast = Toast.makeText(context, content, Toast.LENGTH_LONG);
        } else {
            toast.setText(content);
        }
        toast.show();
    }
}
