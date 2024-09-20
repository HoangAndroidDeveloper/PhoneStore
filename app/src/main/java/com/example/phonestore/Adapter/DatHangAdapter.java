package com.example.phonestore.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.phonestore.MyMethod.MyClass;
import com.example.phonestore.OBJ.DonHang;
import com.example.phonestore.R;

import java.util.List;

public class DatHangAdapter extends RecyclerView.Adapter<DatHangAdapter.DHHolder>{

    List<DonHang> DsDonHang;
    Context context;

    public DatHangAdapter(List<DonHang> dsDonHang, Context context) {
        DsDonHang = dsDonHang;
        this.context = context;
    }

    @NonNull
    @Override
    public DHHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mua,parent,false);
        return new DHHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull DHHolder holder, int position) {
        DonHang dh = DsDonHang.get(position);
        long money = dh.getGia();
        long sl = dh.sl;
        long sumMoney = sl * money;
        String ram = dh.ram;
        String color = dh.color;
        String storage = dh.storage;
        String image = dh.image;
        String name = dh.name;
        holder.moneySP.setText("Giá: "+MyClass.FormatMoney(money));
        holder.sumMoney.setText(MyClass.FormatMoney(sumMoney));
        holder.name.setText(name);
        holder.tongSL.setText("Số lượng: "+sl);
        Glide.with(context).load(image).into(holder.image);
        TextView category = holder.category;
        if(ram == null)
        {
        category.setVisibility(View.GONE);
        }
        else {
            category.setVisibility(View.VISIBLE);
            category.setText("Phân loại: " + color + "(" + ram + "GB-" + storage + "GB)");
        }
    }

    @Override
    public int getItemCount() {
        if (DsDonHang != null)
         return DsDonHang.size();
   return 0;
    }

    public static class  DHHolder extends RecyclerView.ViewHolder {
        TextView tongSL, sumMoney, name, category, moneySP;
        ImageView image;
        public DHHolder(@NonNull View itemView) {
            super(itemView);
            tongSL = itemView.findViewById(R.id.soLuong_spMua);
            moneySP = itemView.findViewById(R.id.money_spMua);
            sumMoney = itemView.findViewById(R.id.sumMoneyMua);
            image = itemView.findViewById(R.id.image_Mua);
            name = itemView.findViewById(R.id.name_spMua);
            category = itemView.findViewById(R.id.category_spMua);
        }
    }
}
