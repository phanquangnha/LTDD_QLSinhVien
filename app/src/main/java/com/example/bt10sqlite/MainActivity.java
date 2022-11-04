package com.example.bt10sqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bt10sqlite.Control.Database;
import com.example.bt10sqlite.Model.Category;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Database db;
    List<Category> categories = new ArrayList<Category>();
    ArrayAdapter<Category> lopArrayAdapter;
    boolean kt=true;
    ListView lvCategory;

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new Database(this,"COMPUTER.sqlite",null,1);
        db.QueryData("create table IF NOT EXISTS Category(idCategory VARCHAR(100),nameCategory nVARCHAR(100))");
        db.QueryData("create table IF NOT EXISTS Computer(idComputer VARCHAR(100),nameComputer VARCHAR(100),idCategory VARCHAR(100))");

        Cursor cursor = db.GetData("select * from Category");
        if (cursor.getCount()==0){
            db.insertCa("20T2","Công nghệ thông tin");
            db.insertCa("20XD1","Xây dựng");
            db.insertCa("19TDH1","Tự động hóa 1");
        }
        cursor = db.GetData("select * from Computer");
        if (cursor.getCount()==0){
            db.insertC("240","Phan Quang Nhã","Category 1");
            db.insertC("241","Phan Quang Nhã","Category 1");
            db.insertC("242","Phan Quang Nhã","Category 1");
            db.insertC("244","Phan Quang Nhã","Category 1");
        }

         lvCategory= (ListView) findViewById(R.id.listviewCategory);

        getDataCategory();
        lvCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(MainActivity.this, ListComputer.class);
                    intent.putExtra("idCategory", categories.get(i).getIdCategory());
                    System.out.println(categories.get(i).getIdCategory());
                    kt=true;
                    startActivity(intent);
            }
        });

        lvCategory.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                int j=i;
                AlertDialog.Builder alertDiaLog = new AlertDialog.Builder(MainActivity.this);
                alertDiaLog.setTitle("Thông báo");
                alertDiaLog.setMessage("Bạn có muốn xóa "+categories.get(i).getIdCategory()+" ?"    );
                alertDiaLog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.QueryData("delete from Category where idCategory ='"+categories.get(j).getIdCategory()+"'");
                        getDataCategory();
                        lopArrayAdapter.notifyDataSetChanged();
                    }
                });
                alertDiaLog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alertDiaLog.show();
                return true;
            }
        });
        Button button = (Button) findViewById(R.id.btn_themCategory);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(MainActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialogcategorycustom);
                dialog.show();
                TextView tv1 = (TextView) dialog.findViewById(R.id.isIDCa);
                TextView tv2 = (TextView) dialog.findViewById(R.id.isNameCa);
                Button btok = (Button) dialog.findViewById(R.id.btn_okCa);
                Button btcancel = (Button) dialog.findViewById(R.id.btn_cancelCa);
                btok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        db.insertCa(tv1.getText().toString().trim(),tv2.getText().toString().trim());
                        dialog.dismiss();
                        getDataCategory();
                        lopArrayAdapter.notifyDataSetChanged();
                    }
                });
                btcancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });
            }
        });
    }

    public void getDataCategory() {
        categories = new ArrayList<Category>();
        Cursor cursor = db.GetData("select * from Category");
        System.out.println("ABC " + cursor.getCount());
        if (cursor.getCount()>0){
            System.out.println("abc");
            while (cursor.moveToNext()){
                @SuppressLint("Range") String idCa = cursor.getString(cursor.getColumnIndex("idCategory"));
                @SuppressLint("Range") String nameCa = cursor.getString(cursor.getColumnIndex("nameCategory"));
                categories.add(new Category(idCa,nameCa));
            }
        }
        lopArrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1,categories);
        lvCategory.setAdapter(lopArrayAdapter);
        lopArrayAdapter.notifyDataSetChanged();
    }
}