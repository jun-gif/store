package com.example.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.db.DBOpenHelper;
import com.example.model.Order;

import java.util.ArrayList;

public class OrderDao {
    private DBOpenHelper helper;
    private SQLiteDatabase db;

    public OrderDao(Context context) {
        helper = new DBOpenHelper(context);
    }
    public long newOrder (Order order){
        db=helper.getReadableDatabase();
        long insertnub=0;
        ContentValues cv=new ContentValues();
        cv.put("user_id",order.getUser_id());
        cv.put("order_distribut_type",order.getOrder_distribut_type());
        cv.put("order_receiver_name",order.getOrder_receiver_name());
        cv.put("order_notes",order.getOrder_notes());
        cv.put("order_status",order.getOrder_status());
        cv.put("order_picPath",order.getOrder_picpath());
        cv.put("distributor_id",order.getDistributor_id());
        cv.put("order_price",order.getOrder_price());
        cv.put("order_receiver_tel",order.getOrder_receiver_tel());
        cv.put("order_receiver_address",order.getOrder_receiver_address());
        cv.put("order_time",order.getOrder_time());
        cv.put("order_delivery_time",order.getOrder_delivery_time());

        insertnub=db.insert("tb_user",null,cv);
        return insertnub;
    }


    public ArrayList<Order> userOrderpsz(String user_id){
        ArrayList<Order> arrayList=new ArrayList<Order>();
        db=helper.getReadableDatabase();
        String sql="select * from tb_order where user_id='"+user_id+"'and order_status=1";
        Cursor cursor= db.rawQuery(sql,null);
        while (cursor.moveToNext()){
            Order order=new Order(); order.setOrder_id(cursor.getLong(cursor.getColumnIndex("order_id")));
            order.setUser_id(cursor.getString(cursor.getColumnIndex("user_id")));
            order.setDistributor_id(cursor.getString(cursor.getColumnIndex("distributor_id")));
            order.setOrder_distribut_type(cursor.getString(cursor.getColumnIndex("order_distribut_type")));
            order.setOrder_price(cursor.getDouble(cursor.getColumnIndex("order_price")));
            order.setOrder_receiver_name(cursor.getString(cursor.getColumnIndex("order_receiver_name")));
            order.setOrder_receiver_address(cursor.getString(cursor.getColumnIndex("order_receiver_address")));
            order.setOrder_receiver_tel(cursor.getLong(cursor.getColumnIndex("order_receiver_tel")));
            order.setOrder_time(cursor.getString(cursor.getColumnIndex("order_time")));
            order.setOrder_delivery_time(cursor.getString(cursor.getColumnIndex("order_delivery_time")));
            order.setOrder_status(cursor.getInt(cursor.getColumnIndex("order_status")));
            order.setOrder_picpath(cursor.getString(cursor.getColumnIndex("order_picpath")));
            order.setOrder_notes(cursor.getString(cursor.getColumnIndex("order_notes")));
            arrayList.add(order);
        }
        cursor.close();
        db.close();
        return arrayList;
    }
    public ArrayList<Order> allOrder(){
        ArrayList<Order> arrayList=new ArrayList<Order>();
        db=helper.getReadableDatabase();
        String sql="select * from tb_order where  order_status=0";
        Cursor cursor= db.rawQuery(sql,null);
        while (cursor.moveToNext()){
            Order order=new Order();
            order.setOrder_id(cursor.getLong(cursor.getColumnIndex("order_id")));
            order.setUser_id(cursor.getString(cursor.getColumnIndex("user_id")));
            order.setDistributor_id(cursor.getString(cursor.getColumnIndex("distributor_id")));
            order.setOrder_distribut_type(cursor.getString(cursor.getColumnIndex("order_distribut_type")));
            order.setOrder_price(cursor.getDouble(cursor.getColumnIndex("order_price")));
            order.setOrder_receiver_name(cursor.getString(cursor.getColumnIndex("order_receiver_name")));
            order.setOrder_receiver_address(cursor.getString(cursor.getColumnIndex("order_receiver_address")));
            order.setOrder_receiver_tel(cursor.getLong(cursor.getColumnIndex("order_receiver_tel")));
            order.setOrder_time(cursor.getString(cursor.getColumnIndex("order_time")));
            order.setOrder_delivery_time(cursor.getString(cursor.getColumnIndex("order_delivery_time")));
            order.setOrder_status(cursor.getInt(cursor.getColumnIndex("order_status")));
            order.setOrder_picpath(cursor.getString(cursor.getColumnIndex("order_picpath")));
            order.setOrder_notes(cursor.getString(cursor.getColumnIndex("order_notes")));
            arrayList.add(order);
        }
        cursor.close();
        db.close();
        return arrayList;
    }






    public void receiveOrder(String id, String order_id) {
        db=helper.getWritableDatabase();

        ContentValues contentValues=new ContentValues();
        contentValues.put("distributor_id",id);
        contentValues.put("order_status",1);

        db.update("tb_order",contentValues,"order_id=?",new String[]{order_id});


    }
}
