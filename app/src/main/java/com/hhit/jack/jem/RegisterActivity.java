package com.hhit.jack.jem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hhit.jack.jem.db.User;
import com.hhit.jack.jem.util.ToastUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_register)
public class RegisterActivity extends BaseActivity {

    private static final String TAG = "RegisterActivity";
    @ViewInject(R.id.register_name)
    private EditText reg_name;
    @ViewInject(R.id.register_pass)
    private EditText reg_pass;
    @ViewInject(R.id.register)
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
        //setContentView(R.layout.activity_register);
    }
    @Event(R.id.register)
    private void register(View view) {
        User user = new User();
        String name=null;
        String pass=null;
        name = reg_name.getText().toString().trim();
        pass = reg_pass.getText().toString().trim();

        if (name.length() != 0 && pass.length() != 0) {
            user.setName(name);
            user.setPass(pass);
            user.setStat(true);
            user.save();        //别忘了存入
            ToastUtil.showToast(this, "注册成功");
            Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            ToastUtil.showToast(this, "输入有错误！！");
        }


    }

}
