package com.example.phonestore.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.phonestore.R;

import java.util.List;

public class SlideAD extends RecyclerView.Adapter<SlideAD.slideHolder> {

    List<String> LImage;
    Context context;

    public SlideAD(List<String> LImage, Context context) {
        this.LImage = LImage;
        this.context = context;
    }

    @NonNull
    @Override
    public slideHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_slide
                ,parent,false);
        return  new slideHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull slideHolder holder, int position) {

        String url = LImage.get(position);
        Glide.with(context).load(url).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return LImage.size();
    }

    public static class slideHolder extends RecyclerView.ViewHolder{
        ImageView image;
        public slideHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_slide);

        }
    }


}
