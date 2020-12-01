package com.example.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.db.DBOpenHelper;
import com.example.model.Distributor;

import java.util.ArrayList;

public class DistributorDao {
    private DBOpenHelper helper;
    private SQLiteDatabase db;
    public DistributorDao(Context context){
        helper=new DBOpenHelper(context);
    }
    public Boolean login(String id,String password){
        db=helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from tb_distributor where distributor_id= '"+ id +"' and distributor_password= '"+ password +"';", null);
        if (cursor.moveToNext()){
            cursor.close();
            db.close();
            return  true;
        }
        cursor.close();
        db.close();
        return  false;
    }

    public Distributor findDistributorById(String id){
        db=helper.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from tb_distributor where distributor_id='"+id+"';",null);
        if (cursor.moveToNext()){
            Distributor distributor=new Distributor();
            distributor.setDistributor_id(cursor.getString(cursor.getColumnIndex("distributor_id")));
            distributor.setDistributor_password(cursor.getString(cursor.getColumnIndex("distributor_password")));
            distributor.setDistributor_name(cursor.getString(cursor.getColumnIndex("distributor_name")));
            distributor.setDistributor_idcar(cursor.getLong(cursor.getColumnIndex("distributor_idcar")));
            distributor.setDistributor_tel(cursor.getLong(cursor.getColumnIndex("distributor_tel")));
            distributor.setDistributor_money(cursor.getDouble(cursor.getColumnIndex("distributor_money")));
            distributor.setDistributor_singularnum(cursor.getInt(cursor.getColumnIndex("distributor_singularnum")));
            distributor.setDistributor_status(cursor.getInt(cursor.getColumnIndex("distributor_status")));
            distributor.setDistributor_picPath(cursor.getString(cursor.getColumnIndex("distributor_picPath")));
            cursor.close();
            db.close();
            return distributor;
        }
        cursor.close();
        db.close();
        return  null;
    }

    public Boolean  addDistributor(Distributor distributor){
        db=helper.getReadableDatabase();
        Cursor cursor=db.rawQuery("INSERT INTO tb_distributor  VALUES('"+distributor.getDistributor_id()+"','"+distributor.getDistributor_password()+"','"+distributor.getDistributor_name()+"','"+distributor.getDistributor_idcar()+"','"+distributor.getDistributor_tel()+"',null,'"+distributor.getDistributor_singularnum()+"',0,'"+distributor.getDistributor_picPath()+"');" ,null);
        if (cursor.moveToNext()){
            cursor.close();
            db.close();
            return true;
        }

        cursor.close();
        db.close();
        return false;
    }


    public ArrayList<Distributor> distributorDesc() {
        ArrayList<Distributor> resultlist=new ArrayList<>();
        db=helper.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from tb_distributor order by distributor_singularnum desc;",null);
        while (cursor.moveToNext()){
            Distributor distributor=new Distributor();
            distributor.setDistributor_id(cursor.getString(cursor.getColumnIndex("distributor_id")));
            distributor.setDistributor_password(cursor.getString(cursor.getColumnIndex("distributor_password")));
            distributor.setDistributor_name(cursor.getString(cursor.getColumnIndex("distributor_name")));
            distributor.setDistributor_idcar(cursor.getLong(cursor.getColumnIndex("distributor_idcar")));
            distributor.setDistributor_tel(cursor.getLong(cursor.getColumnIndex("distributor_tel")));
            distributor.setDistributor_money(cursor.getDouble(cursor.getColumnIndex("distributor_money")));
            distributor.setDistributor_singularnum(cursor.getInt(cursor.getColumnIndex("distributor_singularnum")));
            distributor.setDistributor_status(cursor.getInt(cursor.getColumnIndex("distributor_status")));
            distributor.setDistributor_picPath(cursor.getString(cursor.getColumnIndex("distributor_picPath")));
            resultlist.add(distributor);
        }
        cursor.close();
        db.close();
        return resultlist;
    }

    public void updateSingularnum(String id) {
        db = helper.getWritableDatabase();
        Distributor distributor = findDistributorById(id);
        int num = distributor.getDistributor_singularnum() + 1;
        ContentValues cv = new ContentValues();
        cv.put("distributor_singularnum", num);
        db = helper.getWritableDatabase();
        db.update("tb_distributor", cv, "distributor_id=?", new String[] {id});
    }
}
