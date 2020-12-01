package com.example.activity;

import androidx.appcompat.app.AppCompatActivity;
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
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DeploySentActivity extends AppCompatActivity {


    EditText[]editText=new EditText[4];
    public static final int TAKE_CAMERA = 101;
    private Uri imageUri;//原图保存地址
    ImageView imageView;
    String imagdate;
    String imagepath="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deploy_sent);
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE ,Manifest.permission.READ_PHONE_STATE}, 0);
        }
        invitview();
        findViewById(R.id.takephotobt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    imageUri = FileProvider.getUriForFile(DeploySentActivity.this, "com.example.activity.fileprovider", newFile);
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
            }
        });
        findViewById(R.id.confirm_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String order_distribut_type=getIntent().getStringExtra("order_distribut_type");
                Log.e("tag",order_distribut_type);
                Intent intent=new Intent();
                intent.putExtra("order_distribut_type",order_distribut_type);
                intent.putExtra("name",editText[0].getText().toString());
                intent.putExtra("phone",editText[1].getText().toString());
                intent.putExtra("address",editText[2].getText().toString());
                intent.putExtra("beizhu",editText[3].getText().toString());
                intent.putExtra("picuri",imagepath);
                if(order_distribut_type.equals("bigexp")){
                    intent.putExtra("money","5");
                }else {
                    intent.putExtra("money","3");}
                intent.setClass(DeploySentActivity.this,UserPaymentActivity.class);
                startActivity(intent);
            }
        });
}

    private void invitview() {
        imageView=findViewById(R.id.imges123);
        editText[0]=findViewById(R.id.name);
        editText[1]=findViewById(R.id.haoma);
        editText[2]=findViewById(R.id.address);
        editText[3]=findViewById(R.id.beizhu);
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