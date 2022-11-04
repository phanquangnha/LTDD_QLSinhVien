package com.example.bt10sqlite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.bt10sqlite.Control.ComputerAdapter;
import com.example.bt10sqlite.Model.Computer;
import com.example.bt10sqlite.Control.Database;

import java.util.ArrayList;
import java.util.List;

public class ListComputer extends AppCompatActivity {
    public static RecyclerView recyclerView;
    public static ComputerAdapter adapter;
    public static List<Computer> computers=new ArrayList<Computer>();
    public static Database db;
    public static String idTruyen;

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_computer);


        recyclerView = findViewById(R.id.RecycleViewComputer);
        Intent intent = getIntent();
        idTruyen = intent.getStringExtra("idCategory");
        System.out.println(idTruyen.toString().trim());
        db = new Database(this, "COMPUTER.sqlite", null, 1);
        getDataComputer();

        Button button = (Button) findViewById(R.id.btn_ThemComputer);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(ListComputer.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialogcomputercustom);
                dialog.show();
                TextView tv1 = (TextView) dialog.findViewById(R.id.isIDC);
                TextView tv2 = (TextView) dialog.findViewById(R.id.isNameC);
                TextView tv3 = (TextView) dialog.findViewById(R.id.isIDCate);
                tv3.setText(idTruyen.toString().trim());
                Button btok = (Button) dialog.findViewById(R.id.btn_okC);
                Button btcancel = (Button) dialog.findViewById(R.id.btn_cancelC);
                btok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        db.insertC(tv1.getText().toString().trim(), tv2.getText().toString().trim(), tv3.getText().toString());
                        getDataComputer();
                        dialog.dismiss();

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
        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDataComputer();
            }
        });
    }

    public void getDataComputer() {
        ListComputer.computers = new ArrayList<Computer>();
        Cursor cursor = db.GetData("Select * from Computer where idCategory = '"+idTruyen.toString().trim() +"'");
        while (cursor.moveToNext()){
            @SuppressLint("Range") String idC =cursor.getString(cursor.getColumnIndex("idComputer"));
            @SuppressLint("Range") String nameC = cursor.getString(cursor.getColumnIndex("nameComputer"));
            @SuppressLint("Range") String idCategory = cursor.getString(cursor.getColumnIndex("idCategory"));
            System.out.println(idC+" "+nameC+" "+idCategory);
            computers.add(new Computer(idC,nameC,idCategory));
//            computers.add(new Computer(cursor.getString(cursor.getColumnIndex("idComputer")),cursor.getString(cursor.getColumnIndex("nameComputer")),cursor.getString(cursor.getColumnIndex("idCategory"))));
        }
        adapter = new ComputerAdapter(computers, this);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(this,1);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);
    }
}