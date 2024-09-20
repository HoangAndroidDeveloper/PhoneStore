package com.example.phonestore.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.phonestore.MyMethod.MyClass;
import com.example.phonestore.OBJ.DonHang;
import com.example.phonestore.R;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartHolder>{

    List<DonHang> DsCart;
    Context context;
    public static int check = 0;
    ProcessCheckBox click ;
    public CartAdapter(List<DonHang> dsCart, Context context, ProcessCheckBox click)
    {
        DsCart = dsCart;
        this.context = context;
        this.click = click;
    }
    public interface ProcessCheckBox
    {
        public void clickCheckBox(boolean checked, int position);
    }
    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart,parent,false);
        return new CartHolder(view);
    }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    @Override
    public void onBindViewHolder(@NonNull CartHolder holder, @SuppressLint("RecyclerView") int position)
    {
        DonHang cart = DsCart.get(position);
        String image = cart.image;
        String name = cart.name;
        long sl = cart.sl;
        long slKho = cart.slKho;
        TextView sizeCart = holder.sizeCart;
        TextView slCart = holder.slCart;
        ImageView tang = holder.bt_tang;
        ImageView giam = holder.bt_giam;
        slCart.setText(sl+"");
        long money = cart.gia;
        if(sl == 1)
        {
            giam.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_subtraction_30_unselect));
        }
        else if (sl == slKho)
        {
            tang.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_plus_unselect30));
        }
        tang.setOnClickListener(view -> {

            int sl1 = Integer.parseInt(slCart.getText().toString());

            if(sl1 < slKho)
            {
                sl1++;
                cart.sl = sl1;
                slCart.setText(sl1 + "");
                if(sl1 == slKho)
                    tang.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_plus_unselect30));
            }
            if(sl1 == 2)
            {
                giam.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_subtraction_30));
            }

        });
        giam.setOnClickListener(view -> {

            int number = Integer.parseInt(slCart.getText().toString());
            number--;
            if(number == slKho-1)
                tang.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_plus_30));
            if(number >= 1)
            {
                cart.sl = number;
                slCart.setText("" + number);
            }
            if(number == 1)
            {
                giam.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_subtraction_30_unselect));
            }
        });
        if(cart.ram != null)
        {
            String color = cart.color;
            String ram = cart.ram;
            String storage = cart.storage;
            sizeCart.setText(color +"("+ram+"-"+storage+")");
        }
        else
        {
            sizeCart.setVisibility(View.GONE);
        }
     Glide.with(context).load(image).into(holder.imageCart);
     holder.NameCart.setText(name);
     holder.moneyCart.setText(MyClass.FormatMoney(money));
     CheckBox checkBox = holder.checkBoxCart;
     checkBox.setChecked(check != 0);
     boolean isCheck = checkBox.isChecked();
     click.clickCheckBox(isCheck,position);
     checkBox.setOnCheckedChangeListener((compoundButton, b) ->
     {
         click.clickCheckBox(b,position);
     });
    }

    @Override
    public int getItemCount() {
        if(DsCart != null)
        {
            return DsCart.size();
        }
        return 0;
    }

    public static class CartHolder extends RecyclerView.ViewHolder {
        CheckBox checkBoxCart;
        ImageView imageCart ,bt_tang, bt_giam;
        TextView NameCart, sizeCart, moneyCart, slCart;

        public CartHolder(@NonNull View itemView) {
            super(itemView);
            checkBoxCart = itemView.findViewById(R.id.checkboxCart);
            imageCart = itemView.findViewById(R.id.ImageCart);
            NameCart = itemView.findViewById(R.id.NameCart);
            sizeCart = itemView.findViewById(R.id.sizeCart);
            moneyCart = itemView.findViewById(R.id.moneyCart);
            bt_tang = itemView.findViewById(R.id.bt_tang);
            bt_giam = itemView.findViewById(R.id.bt_giam);
            slCart = itemView.findViewById(R.id.sl_dialog);
        }
    }
}
