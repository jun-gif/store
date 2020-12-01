package com.example.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dao.DistributorDao;
import com.example.model.Distributor;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DistribRegisterActivity extends AppCompatActivity implements View.OnClickListener {
    DistributorDao distributorDao=new DistributorDao(this);
    EditText distributorId_et,password_et,distributorName_et,telphone_et,idcard_et;
    Toolbar toolbar;
    Button takephotobt;
    public static final int TAKE_CAMERA = 101;
    private Uri imageUri;//原图保存地址
    ImageView imageView;
    String imagdate;
    String imagepath ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distrib_register);
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE}, 0);
        }
        invitview();
    }
    private void invitview() {
        distributorId_et=findViewById(R.id.distributor_id);
        password_et=findViewById(R.id.password);
        distributorName_et=findViewById(R.id.distributor_name);
        telphone_et=findViewById(R.id.telphone);
        idcard_et=findViewById(R.id.distributor_idcar);
        takephotobt=findViewById(R.id.takephotobt);
        imageView=findViewById(R.id.imges123);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        findViewById(R.id.goback).setOnClickListener(this);
        findViewById(R.id.registerbt).setOnClickListener(this);
        takephotobt.setOnClickListener(this);
    }
    private  void formatCheck() {
        Pattern p = Pattern.compile("^d_[a-zA-z_0-9]{3,5}");
        Matcher m = p.matcher(distributorId_et.getText().toString());
        Pattern p_phone = Pattern.compile("^1[3,5,8,7][0-9]{9}");
        Matcher m_phone = p_phone.matcher(telphone_et.getText().toString());
        String regex = "^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$";
        Pattern p_idcar = Pattern.compile(regex);
        Matcher m_idcar = p_idcar.matcher(idcard_et.getText().toString());
        Distributor distributor=distributorDao.findDistributorById(distributorId_et.getText().toString());
        if (distributorId_et.getText().toString().indexOf("d")<0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(DistribRegisterActivity.this);
            builder.setTitle("提示");
            builder.setMessage("输入账号格式错误");
            builder.setPositiveButton("确定", null);
            builder.show();
        } else {
            handleRegister(distributor,m,m_phone,m_idcar);
        }
    }
    public  void handleRegister(Object obj,Matcher m,Matcher m_phone,Matcher m_idcar){
        if (obj != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(DistribRegisterActivity.this);
            builder.setTitle("提示");
            builder.setMessage("该配送员名已存在");
            builder.setPositiveButton("确定", null);
            builder.show();
        } else if (!m.matches()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(DistribRegisterActivity.this);
            builder.setTitle("提示");
            builder.setMessage("输入账号格式错误");
            builder.setPositiveButton("确定", null);
            builder.show();
        } else if (!m_phone.matches()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(DistribRegisterActivity.this);
            builder.setTitle("提示");
            builder.setMessage("输入电话格式错误");
            builder.setPositiveButton("确定", null);
            builder.show();
        }else if (!m_idcar.matches()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(DistribRegisterActivity.this);
            builder.setTitle("提示");
            builder.setMessage("身份证号有误请重新输入");
            builder.setPositiveButton("确定", null);
            builder.show();
        }else {
            Distributor distributor=new Distributor();
            distributor.setDistributor_id(distributorId_et.getText().toString());
            distributor.setDistributor_password(password_et.getText().toString());
            distributor.setDistributor_name(distributorName_et.getText().toString());
            distributor.setDistributor_idcar(Long.parseLong(idcard_et.getText().toString()));
            distributor.setDistributor_tel(Long.parseLong(telphone_et.getText().toString()));
            distributor.setDistributor_picPath(imagepath);
            distributorDao.addDistributor(distributor);
            Toast.makeText(this,"注册成功",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent();
            intent.putExtra("userId",distributor.getDistributor_id());
            intent.setClass(DistribRegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.goback:
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
            case R.id.clear:
                distributorId_et.setText("");
                password_et.setText("");
                telphone_et.setText("");
                distributorName_et.setText("");
                idcard_et.setText("");
                break;
            case R.id.takephotobt:
                File imagePath = new File(getFilesDir(), "myimages");//gn file_paths.xml文件中的files-path标签中的path值一致
                if (!imagePath.exists()) {
                    imagePath.mkdirs();
                }
                imagdate = new SimpleDateFormat("yyyy_MMdd_hhmmss").format(new Date());
                File newFile = new File(imagePath, imagdate + ".jpg");
                imagepath=newFile.getPath();
                System.out.println("newFile.getPath()="+newFile.getPath());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    //大于等于版本24（7.0）的场合
                    imageUri = FileProvider.getUriForFile(DistribRegisterActivity.this, "com.example.activity.fileprovider", newFile);
                    //此处的outputImage指定的路径要在file_paths.xml文件对应配置path指定路径的子路径
                } else {
                    //小于android 版本7.0（24）的场合
                    imageUri = Uri.fromFile(newFile);
                }
                //启动相机程序
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //MediaStore.ACTION_IMAGE_CAPTURE = android.media.action.IMAGE_CAPTURE
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri); //将拍取的照片保存到指定URL
                startActivityForResult(intent, TAKE_CAMERA);
                break;
            case R.id.registerbt:
                formatCheck();
                break;
        }

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_CAMERA://请求码
                if (resultCode == RESULT_OK) {//结果标识
                    try {
                        // 将拍摄的照片显示出来
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        imageView.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:break;
        }
    }
}