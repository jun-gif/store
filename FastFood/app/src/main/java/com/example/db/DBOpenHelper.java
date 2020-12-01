package com.example.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBOpenHelper extends SQLiteOpenHelper {
    final static String DB_Name="food";
    final static int VERTION=1;
    public DBOpenHelper(@Nullable Context context) {
        super(context, DB_Name, null, VERTION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建用户表
        db.execSQL("CREATE TABLE `tb_user`("+
                "`user_id` varchar(10) not null,"+
                "`user_password`  not null,"+
                "`user_name` varchar(20) ,"+
                "`user_tel`  ,"+
                "`user_money` double(20,0),"+
                "`user_address` varchar(200) ,"+
                "`user_status` int not null,"+
                "`user_picPath` varchar(200),"+
                "primary key(`user_id`));");
        db.execSQL("insert into tb_user values('u1','123','小天','15982274685','50','1623','0','');");

        //创建商家表
        db.execSQL("create table `tb_shop`("+
                "`shop_id` not null,"+
                "`shop_password` varchar(20) not null,"+
                "`shop_name` varchar(20) not null,"+
                "`shop_tel` integer(11) not null,"+
                "`shop_money` double(20,0),"+
                "`shop_singularnum` int not null,"+
                "`shop_status` int not null,"+
                "`shop_picPath` varchar(200),"+
                "primary key(`shop_id`));");
        db.execSQL("insert into tb_shop values('s1','123','黄焖鸡米饭','12345678910','0','0','0','');");
        db.execSQL("insert into tb_shop values('s2','123','土耳其烤肉饭','12345678910','0','0','0','');");
        db.execSQL("insert into tb_shop values('s3','123','麻辣香锅','12345678910','0','0','0','');");
        db.execSQL("insert into tb_shop values('s4','123','美味拉面','12345678910','0','0','0','');");
        db.execSQL("insert into tb_shop values('s5','123','漂汤饭','12345678910','0','0','0','');");
        //创建菜品表
        db.execSQL("create table tb_food("+
                "food_id INTEGER primary key autoincrement not null,"+
                "food_name varchar(50) not null,"+
                "food_price double(5) not null,"+
                "food_introduce varchar(100),"+
                "food_picPath varchar(200),"+
                "shop_id varchar(10) not null,"+
                "foodtype_id varchar(10) not null,"+
                "recommand int not null);");
        db.execSQL("insert into tb_food values('1','黄焖鸡','10','用大块新鲜鸡肉和土豆做成','','s1','1','0');");
        //创建菜品类型表
        db.execSQL("create table tb_foodtype("+
                "foodtype_id INTEGER  primary key autoincrement not null,"+
                "foodtype_name varchar(50) not null);");
        db.execSQL("insert into tb_foodtype values('1','鲁菜')");

        //创建订单表
        db.execSQL("create table`tb_order`("+
                "order_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "user_id varchar(10) not null,"+
                "food_id INTEGER not null,"+
                "order_num int(10) not null,"+
                "order_price double(20,0) not null,"+
                "order_receiver_name varchar(20) not null,"+
                "order_receiver_tel varchar(11) not null,"+
                "order_receiver_address varchar(200) not null,"+
                "order_time datetime not null,"+
                "order_delivery_time datetime ,"+
                "order_status int not null,"+
                "order_notes varchar(300))"
        );
        db.execSQL("insert into tb_order values(null,'u1','1','2','20','小天','15982274685','1623','2020/09/10 18:06:23',null,'0','加辣')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
