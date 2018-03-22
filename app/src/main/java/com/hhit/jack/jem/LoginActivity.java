package com.hhit.jack.jem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.hhit.jack.jem.db.User;
import com.hhit.jack.jem.util.ToastUtil;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.List;


/**
 * A login screen that offers login via email/password.
 */
@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity{

    @ViewInject(R.id.login)
    private Button login;
    @ViewInject(R.id.register)
    private Button register;
    @ViewInject(R.id.account)
    private EditText account;
    @ViewInject(R.id.password)
    private EditText password;
    @ViewInject(R.id.remember_pass)
    private CheckBox checkable;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    //final MyApplication app= (MyApplication) getApplication();
    List<User> users = DataSupport.findAll(User.class);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_login);  //用注解替代
        Connector.getDatabase();

        //List<User> users = DataSupport.findAll(User.class);
        for(User user : users){
            if (user.isStat()) {
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        }

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        boolean isRemember = preferences.getBoolean("remember_password",false);

        if (isRemember) {
            String acc = preferences.getString("account","");
            String pas = preferences.getString("password","");
            account.setText(acc);
            password.setText(pas);
            checkable.setChecked(true);
        }
    }

    @Event(R.id.login)
    private void login(View view) {
        String id = account.getText().toString();
        String ps = password.getText().toString();
        int i=0;

        for (User user: users) {
            if(id.equals(user.getName()) && ps.equals(user.getPass())) {
                editor = preferences.edit();

                if (checkable.isChecked()) {
                    editor.putString("account",id);
                    editor.putString("password",ps);
                    editor.putBoolean("remember_password",true);
                } else {
                    editor.clear();
                }
                editor.apply();
                user.setStat(true);
                user.save();

                //app.setUser(user);
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
            i++;
        }
        if (i>users.size() || users.size()==0){
            ToastUtil.showToast(LoginActivity.this, "用户名或密码错误！");
        }

    }

    @Event(R.id.register)
    private void register(View view) {
        //Toast.makeText(LoginActivity.this,"注册",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }
}

