package com.hhit.jack.jem;

import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hhit.jack.jem.db.User;

import org.litepal.crud.DataSupport;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

@ContentView(R.layout.activity_user)
public class UserActivity extends BaseActivity {
    @ViewInject(R.id.sex)
    EditText sex;
    @ViewInject(R.id.phone)
    EditText phone;
    @ViewInject(R.id.motto)
    EditText motto;
    @ViewInject(R.id.submit)
    Button submit;
    List<User> users;
    private static final String TAG = "UserActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
        //setContentView(R.layout.activity_user);
    }

    @Event(R.id.submit)
    private void setSubmit(View view){
        String sexString = sex.getText().toString();
        String phoneString = phone.getText().toString();
        String mottoString = motto.getText().toString();

//        ContentValues values = new ContentValues();
//        values.put("sex", sexString);
//        values.put("phone", phoneString);
//        values.put("sign", mottoString);
//
//        DataSupport.update(User.class, values, 1);

        users = DataSupport.findAll(User.class);
        users.get(0).setSex(sexString);
        users.get(0).setPhone(phoneString);
        users.get(0).setSign(mottoString);
        users.get(0).save();
        finish();
    }
}
