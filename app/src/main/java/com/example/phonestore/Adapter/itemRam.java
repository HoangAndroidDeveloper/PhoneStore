package com.example.phonestore.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonestore.R;

import java.util.List;

public class itemRam extends RecyclerView.Adapter<itemRam.colorHolder>
{
   List<String> LRam;
   public int position = -1;
   ItemColor.colorClick colorClick;

    public itemRam(List<String> LRam, ItemColor.colorClick colorClick) {
        this.LRam = LRam;
        this.colorClick = colorClick;
    }


    @SuppressLint("NotifyDataSetChanged")
    public void isSelected(int position)
    {
        this.position = position;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public colorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_color,parent,false);
        return new colorHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull colorHolder holder, int position)
    {
        TextView tRam = holder.ram;
        String version = LRam.get(position);
        String[] chuoiCon = version.split("-");
        String ram = chuoiCon [0];
        String storage = chuoiCon [1];
        tRam.setText(ram + "GB"+"-"+storage+"GB");
        if(this.position == position)
            tRam.setBackgroundResource(R.drawable.custom_item_version_selected);
        else
            tRam.setBackgroundResource(R.drawable.custom_item_version);
        tRam.setOnClickListener(view -> {
          colorClick.onClick(version,position);
        });
    }

    @Override
    public int getItemCount()
    {
        if(LRam.isEmpty())
         return 0;
        return LRam.size();
    }

    public static class colorHolder extends RecyclerView.ViewHolder
    {
        TextView ram;
        public colorHolder(@NonNull View itemView) {
            super(itemView);
            ram = itemView.findViewById(R.id.text);
        }
    }
}
