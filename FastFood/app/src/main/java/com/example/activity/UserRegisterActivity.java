package com.example.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dao.UserDao;
import com.example.model.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserRegisterActivity extends AppCompatActivity implements View.OnClickListener {
    UserDao userDao=new UserDao(this);
    EditText userid_et,username_et,telphone_et,address_et,password_et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        userid_et=findViewById(R.id.userid);
        username_et=findViewById(R.id.username);
        telphone_et=findViewById(R.id.telphone);
        address_et=findViewById(R.id.address);
        password_et=findViewById(R.id.password);
        findViewById(R.id.registe).setOnClickListener(this);
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.reset).setOnClickListener(this);
    }

    //格式验证
    private  void formatCheck() {
        Pattern p = Pattern.compile("^u_[a-zA-z_0-9]{3,5}");
        Matcher m = p.matcher(userid_et.getText().toString());
        Pattern p_phone=Pattern.compile("^1[3,5,8,7][0-9]{9}");
        Matcher m_phone = p_phone.matcher(telphone_et.getText().toString());
        User user = userDao.findUser(userid_et.getText().toString());
        if (userid_et.getText().toString().indexOf("u")<0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(UserRegisterActivity.this);
            builder.setTitle("提示");
            builder.setMessage("输入账号格式错误");
            builder.setPositiveButton("确定", null);
            builder.show();
        } else {
            handleRegister(user, m, m_phone);
        }
    }
    //处理注册信息
    public  void handleRegister(Object o,Matcher m,Matcher m_phone){
        if (o != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(UserRegisterActivity.this);
            builder.setTitle("提示");
            builder.setMessage("该用户名已存在");
            builder.setPositiveButton("确定", null);
            builder.show();
        } else if (!m.matches()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(UserRegisterActivity.this);
            builder.setTitle("提示");
            builder.setMessage("输入账号格式错误");
            builder.setPositiveButton("确定", null);
            builder.show();
        } else if (!m_phone.matches()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(UserRegisterActivity.this);
            builder.setTitle("提示");
            builder.setMessage("输入电话格式错误");
            builder.setPositiveButton("确定", null);
            builder.show();
        }else {
            User user = new User();
            user.setUser_id(userid_et.getText().toString());
            user.setUser_password(password_et.getText().toString());
            user.setUser_name(username_et.getText().toString());
            user.setUser_tel(Long.parseLong(telphone_et.getText().toString()));
//            user.setUser_picPath(imagepath);
            user.setUser_address(address_et.getText().toString());
            if(userDao.addUser(user)>0){
                Toast.makeText(this,"注册成功",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent();
                intent.putExtra("userId",user.getUser_id());
                intent.setClass(UserRegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
            else {
                Toast.makeText(this,"注册失败",Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.registe:
                formatCheck();
                break;
            case R.id.reset:
                userid_et.setText("");
                password_et.setText("");
                telphone_et.setText("");
                username_et.setText("");
                address_et.setText("");
                break;
            case R.id.back:
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;

        }
    }
}