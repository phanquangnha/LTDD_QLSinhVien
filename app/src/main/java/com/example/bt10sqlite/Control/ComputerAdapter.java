package com.example.bt10sqlite.Control;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.opengl.Visibility;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bt10sqlite.ListComputer;
import com.example.bt10sqlite.MainActivity;
import com.example.bt10sqlite.Model.Computer;
import com.example.bt10sqlite.R;

import java.util.ArrayList;
import java.util.List;

public class ComputerAdapter extends RecyclerView.Adapter<ComputerAdapter.ViewHolder> {
    //Dữ liệu hiện thị là danh sách sinh viên
    private List<Computer> mComputer;
    // Lưu Context để dễ dàng truy cập
    private Context mContext;

    public ComputerAdapter(List<Computer> computers, Context mContext) {
        this.mComputer = computers;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ComputerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_computeritem, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ComputerAdapter.ViewHolder holder, int position) {
        Computer computer =mComputer.get(position);
        holder.studentname.setText(computer.getIdComputer());
        holder.birthyear.setText(computer.getNameComputer());
    }

    @Override
    public int getItemCount() {
        if (mComputer!=null)
        return mComputer.size();
        else return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private View itemview;
        public TextView studentname;
        public TextView birthyear;
        public Button btnDel,btnEdit;

        public ViewHolder(View itemView) {
            super(itemView);
            itemview = itemView;
            studentname = itemView.findViewById(R.id.studentname);
            birthyear = itemView.findViewById(R.id.birthyear);
            btnDel = itemView.findViewById(R.id.btnDel);
            btnEdit = itemview.findViewById(R.id.btnEdit);


            //Xử lý khi nút Chi tiết được bấm
            btnDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder alertDiaLog = new AlertDialog.Builder(view.getContext());
                    alertDiaLog.setTitle("Thông báo");
                    alertDiaLog.setMessage("Bạn có muốn xóa "+studentname.getText().toString().trim()+" ?"    );
                    alertDiaLog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ListComputer.db.QueryData("delete from Computer where idComputer ='"+studentname.getText().toString().trim()+"'");
                            ListComputer.computers = new ArrayList<Computer>();
                            Cursor cursor = ListComputer.db.GetData("Select * from Computer where idCategory = '"+ListComputer.idTruyen.toString().trim() +"'");
                            while (cursor.moveToNext()){
                                @SuppressLint("Range") String idC =cursor.getString(cursor.getColumnIndex("idComputer"));
                                @SuppressLint("Range") String nameC = cursor.getString(cursor.getColumnIndex("nameComputer"));
                                @SuppressLint("Range") String idCategory = cursor.getString(cursor.getColumnIndex("idCategory"));
                                System.out.println(idC+" "+nameC+" "+idCategory);
                                ListComputer.computers.add(new Computer(idC,nameC,idCategory));
//            computers.add(new Computer(cursor.getString(cursor.getColumnIndex("idComputer")),cursor.getString(cursor.getColumnIndex("nameComputer")),cursor.getString(cursor.getColumnIndex("idCategory"))));
                            }
                            ListComputer.adapter = new ComputerAdapter(ListComputer.computers, view.getContext());
                            GridLayoutManager linearLayoutManager = new GridLayoutManager(view.getContext(), 1);
                            ListComputer.recyclerView.setAdapter(ListComputer.adapter);
                            ListComputer.recyclerView.setLayoutManager(linearLayoutManager);
                        }
                    });
                    alertDiaLog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    alertDiaLog.show();
                }
            });
            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Dialog dialog = new Dialog(view.getContext());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.dialogcomputercustom);
                    dialog.show();
                    TextView tv1 = (TextView) dialog.findViewById(R.id.isIDC);
                    TextView tv2 = (TextView) dialog.findViewById(R.id.isNameC);
                    TextView tv3 = (TextView) dialog.findViewById(R.id.isIDCate);
                    tv3.setVisibility(View.GONE);
                    Button btok = (Button) dialog.findViewById(R.id.btn_okC);
                    Button btcancel = (Button) dialog.findViewById(R.id.btn_cancelC);

                    btok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ListComputer.db.QueryData("update Computer set idComputer = '"+tv1.getText().toString().trim()+ "',nameComputer='"+ tv2.getText().toString().trim()+ "'  where idComputer ='"+studentname.getText().toString().trim()+"'");
                            ListComputer.computers = new ArrayList<Computer>();
                            Cursor cursor = ListComputer.db.GetData("Select * from Computer where idCategory = '"+ListComputer.idTruyen.toString().trim() +"'");
                            while (cursor.moveToNext()){
                                @SuppressLint("Range") String idC =cursor.getString(cursor.getColumnIndex("idComputer"));
                                @SuppressLint("Range") String nameC = cursor.getString(cursor.getColumnIndex("nameComputer"));
                                @SuppressLint("Range") String idCategory = cursor.getString(cursor.getColumnIndex("idCategory"));
                                System.out.println(idC+" "+nameC+" "+idCategory);
                                ListComputer.computers.add(new Computer(idC,nameC,idCategory));
//            computers.add(new Computer(cursor.getString(cursor.getColumnIndex("idComputer")),cursor.getString(cursor.getColumnIndex("nameComputer")),cursor.getString(cursor.getColumnIndex("idCategory"))));
                            }
                            ListComputer.adapter = new ComputerAdapter(ListComputer.computers, view.getContext());
                            GridLayoutManager linearLayoutManager = new GridLayoutManager(view.getContext(), 1);
                            ListComputer.recyclerView.setAdapter(ListComputer.adapter);
                            ListComputer.recyclerView.setLayoutManager(linearLayoutManager);
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
        }
    }


}
