package com.example.bt10sqlite.Control;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //query
    public void QueryData(String sql){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
    }

    public void insertComputer(String idC,String nameC,String idCa){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO tblop VALUES(?,?,?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(0,idC);
        statement.bindString(1,nameC);
        statement.bindString(3,idCa);

        statement.executeInsert();
    }
    public long insertC(String idC,String nameC,String idCa) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("idComputer", idC);
        values.put("nameComputer", nameC);
        values.put("idCategory", idCa);
        return db.insert("Computer", null, values);
    }
    public long insertCa(String idC,String nameC) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("idCategory", idC);
        values.put("nameCategory", nameC);
        return db.insert("Category", null, values);
    }
    public void insertCategory(String idCa,String nameCa){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO tblop VALUES(?,?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(0,idCa);
        statement.bindString(1,nameCa);
        statement.executeInsert();
    }
    public Boolean update_img(){
        return true;
    }
    public Cursor GetData(String sql){
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(sql,null);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
