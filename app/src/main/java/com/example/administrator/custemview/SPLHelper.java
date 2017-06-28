package com.example.administrator.custemview;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mavin on 2017/6/28.
 */

public class SPLHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME1 = "citys.db"; //数据库名称
    public static final String TABLE_NAME = "citys"; //数据库名称

    private static final int version = 1; //数据库版本

    public SPLHelper(Context context) {
        super(context, TABLE_NAME1, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("CREATE TABLE IF NOT EXISTS citys (province varchar(20),city varchar(20),district varchar(20))");
        String sql = "CREATE TABLE " + TABLE_NAME + "("
                +" id INTEGER PRIMARY KEY AUTOINCREMENT,"
                +" province TEXT,"
                +" city TEXT,"
                +" district TEXT"
                + ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql  = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
        this.onCreate(db);
    }
}
