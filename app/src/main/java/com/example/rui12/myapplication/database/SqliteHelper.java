package com.example.rui12.myapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteHelper extends SQLiteOpenHelper {
    //数据库版本
    private static final int DATABASE_VERSION = 1;

    //数据库名称
    private static final String DATABASE_NAME = "DREAM.db";
    //表名称
    private static final String TABLE_NAME = "USER";


    public SqliteHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建数据表
        String CREATE_TABLE_STUDENT="CREATE TABLE "+ TABLE_NAME +"("
                + "id"+" INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + "username" + " TEXT,"
                + "password" + " INTEGER,"
                + "phone"    + " TEXT,"
                + "age"      + "INTEGER,"
                + "sex"      + "INTEGER,"
                + "timestamp"+ "INTEGER,"
                + "token"    + "TEXT)";
        db.execSQL(CREATE_TABLE_STUDENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //如果旧表存在，删除，所以数据将会消失
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        //再次创建表
        onCreate(db);
    }
}
