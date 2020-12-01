package com.example.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.db.DBOpenHelper;
import com.example.model.User;

public class UserDao {
    private DBOpenHelper helper;
    private SQLiteDatabase db;
    public UserDao(Context context){
        helper=new DBOpenHelper(context);
    }
    /**
     *
     * 用户登录
     * */
    public Boolean login(String user_id,String password){
        db=helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from tb_user where user_id= '"+ user_id +"' and user_password= '"+ password +"';", null);
        if (cursor.moveToNext()){
            cursor.close();
            db.close();
            return  true;
        }
        cursor.close();
        db.close();
        return  false;
    }

    /**
     * 根据id查找用户信息
     *
     * */
    public User findUser(String user_id){
        db=helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from tb_user where user_id='" + user_id + "';", null);
        if (cursor.moveToNext()){
            User user=new User();
            user.setUser_id(cursor.getString(cursor.getColumnIndex("user_id")));
            user.setUser_password(cursor.getString(cursor.getColumnIndex("user_password")));
            user.setUser_name(cursor.getString(cursor.getColumnIndex("user_name")));
            user.setUser_tel(cursor.getLong(cursor.getColumnIndex("user_tel")));
            user.setUser_money(cursor.getDouble(cursor.getColumnIndex("user_money")));
            user.setUser_address(cursor.getString(cursor.getColumnIndex("user_address")));
            user.setUser_statue(cursor.getInt(cursor.getColumnIndex("user_status")));
            user.setUser_picPath(cursor.getString(cursor.getColumnIndex("user_picPath")));
            return user;
        }
        return null;
    }

    /**
     * 新建用户
     *
     * @return*/
    public long addUser(User user){
        db=helper.getWritableDatabase();
        long insertflag=0;
//        Cursor cursor=db.rawQuery("insert into tb_user values ('"+ user.getUser_id()+"','"+ user.getUser_password()+"','"+ user.getUser_name()+"','"+ user.getUser_tel()+"','','"+ user.getUser_address()+"','0','');",null);
//        if (cursor.moveToNext()){
//            cursor.close();
//            db.close();
//            return true;
//        }
//
//        cursor.close();
//        db.close();
//        return false;
        ContentValues contentValues=new ContentValues();
        contentValues.put("user_id",user.getUser_id());
        contentValues.put("user_password",user.getUser_password());
        contentValues.put("user_tel",user.getUser_tel());
        contentValues.put("user_name",user.getUser_name());
        contentValues.put("user_address",user.getUser_address());
        contentValues.put("user_money",100);
        contentValues.put("user_status",0);
        contentValues.put("user_picPath",user.getUser_picPath());
        insertflag=db.insert("tb_user",null,contentValues);

        return insertflag;
    }
}
