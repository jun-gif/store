package com.example.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SentExpActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sent_exp);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        findViewById(R.id.fadajian).setOnClickListener(this);
        findViewById(R.id.faxiaojian).setOnClickListener(this);
        findViewById(R.id.goback).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fadajian:
                Intent intent=new Intent();
                intent.putExtra("order_distribut_type","sentbigexp");
                intent.setClass(SentExpActivity.this, DeploySentActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.faxiaojian:
                Intent intent1=new Intent();
                intent1.putExtra("order_distribut_type","sentsmallexp");
                intent1.setClass(SentExpActivity.this, DeploySentActivity.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.goback:
                finish();
                break;
        }
    }
}