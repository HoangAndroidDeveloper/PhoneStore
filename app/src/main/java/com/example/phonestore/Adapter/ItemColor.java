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

public class ItemColor extends RecyclerView.Adapter<ItemColor.colorHolder>
{
   List<String> LColor;
   colorClick colorClick;

    public ItemColor(List<String> LColor, ItemColor.colorClick colorClick) {
        this.LColor = LColor;
        this.colorClick = colorClick;
    }

    public int position = -1;
   public interface colorClick
   {
       public  void onClick(String color, int position);
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

    @Override
    public void onBindViewHolder(@NonNull colorHolder holder, int position)
    {
        TextView tColor = holder.color;
        String color = LColor.get(position);
        tColor.setText(color);
        tColor.setOnClickListener(view -> {
          colorClick.onClick(color,position);
        });
        if(this.position == position)
            tColor.setBackgroundResource(R.drawable.custom_item_version_selected);
        else
            tColor.setBackgroundResource(R.drawable.custom_item_version);
    }

    @Override
    public int getItemCount()
    {
        if(LColor.isEmpty())
         return 0;
        return LColor.size();
    }

    public static class colorHolder extends RecyclerView.ViewHolder
    {
        TextView color;
        public colorHolder(@NonNull View itemView) {
            super(itemView);
            color = itemView.findViewById(R.id.text);
        }
    }
}
