package com.example.phonestore.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonestore.Activity.Search;
import com.example.phonestore.R;

public class DanhMucAD extends RecyclerView.Adapter<DanhMucAD.DMHolder>{

    String [] LDMuc;
    Context context;

    public DanhMucAD(String[] LDMuc, Context context) {
        this.LDMuc = LDMuc;
        this.context = context;
    }

    @NonNull
    @Override
    public DMHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_danh_muc,parent,false);
        return new DMHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull DMHolder holder, int position) {
        String dm = LDMuc[position];
        TextView nameDM = holder.nameDM;
        nameDM.setText(dm);
        nameDM.setOnClickListener(v -> {
            Intent it = new Intent(context, Search.class);
            it.putExtra("keyword",dm);
            context.startActivity(it);
        });
    }

    @Override
    public int getItemCount() {
            return LDMuc.length;
    }

    public static class DMHolder extends RecyclerView.ViewHolder {
        TextView nameDM ;
        public DMHolder(@NonNull View itemView) {
            super(itemView);
            nameDM = itemView.findViewById(R.id.name_danh_muc);
        }
    }
}
