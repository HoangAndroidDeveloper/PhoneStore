package com.example.phonestore.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.phonestore.MyMethod.MyClass;
import com.example.phonestore.OBJ.Phone;
import com.example.phonestore.R;

import java.util.List;

public class SP_AD extends RecyclerView.Adapter<SP_AD.SPHolder> {
    Context context;
    public List<Phone> LPhone;
    SPClick spClick;
    public interface SPClick{
        public void clickSP(Phone phone);
    }

    public SP_AD(Context context, List<Phone> LPhone, SPClick spClick)
    {
        this.context = context;
        this.LPhone = LPhone;
        this.spClick = spClick;
    }

    @NonNull
    @Override
    public SPHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sp,parent,false);

        return new SPHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SPHolder holder, int position)
    {
        String  name;
        Phone phone = LPhone.get(position);
        name = phone.getName();
        long gia = phone.getGia() ;
        holder.Gia.setText(MyClass.FormatMoney(gia));
        String image = phone.getImage().get(0);
        holder.name.setText(name);
        holder.ly_sp.setOnClickListener(view -> {
            spClick.clickSP(phone);
        });
        Glide.with(context).load(Uri.parse(image)).into(holder.image);
    }

    @Override
    public int getItemCount() {

        if(LPhone.isEmpty())
            return 0;
        return LPhone.size();
    }
    public static class SPHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name,Gia;
        CardView ly_sp;
        public SPHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_sp);
            name = itemView.findViewById(R.id.name_sp);
            Gia = itemView.findViewById(R.id.money_sp);
            ly_sp = itemView.findViewById(R.id.ly_sp);
        }
    }
}
