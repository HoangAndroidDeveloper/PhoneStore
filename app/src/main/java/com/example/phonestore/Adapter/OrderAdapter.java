package com.example.phonestore.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.phonestore.MyMethod.InFoStatic;
import com.example.phonestore.MyMethod.MyClass;
import com.example.phonestore.OBJ.DonHang;
import com.example.phonestore.OBJ.User;
import com.example.phonestore.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OderHolder> {
    List<DonHang> DsOrder;
    Context context;

    public OrderAdapter(List<DonHang> dsOrder, Context context)
    {
        DsOrder = dsOrder;
        this.context = context;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<DonHang> dsOrder)
    {
        DsOrder = dsOrder;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent
                , false);
        return new OderHolder(view);
    }

    @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
    @Override
    public void onBindViewHolder(@NonNull OderHolder holder, int position)
    {
        DonHang order = DsOrder.get(position);
        int sl = (int) order.getSl();
        String state = order.getState();
        long money = order.gia;
        long sumMoney = money * sl + 20000;
        String color = order.getColor();
        String ram = order.getRam();
        String st = order.getStorage();
        Glide.with(context).load(order.getImage()).into(holder.image);
        holder.nameOrder.setText(order.getName());
        TextView ColorAndSize = holder.ColorAndSize;
        if(color != null)
        {
           ColorAndSize.setText(color + " (" + ram + "-" + st + ")");
           ColorAndSize.setVisibility(View.VISIBLE);
        }
        else
        {
            ColorAndSize.setVisibility(View.GONE);
        }
        holder.money1.setText(MyClass.FormatMoney(money));
        holder.moneyTong.setText(MyClass.FormatMoney(sumMoney));
        holder.SL.setText("x"+sl);
        if(InFoStatic.user == null)
            InFoStatic.user = User.checkLogin();
        holder.stateOrder.setText(order.getState());
        Button bt_cancel = holder.bt_cancel;
        if(state.equals("Đã được hủy bởi bạn"))
        {
           bt_cancel.setText("Chi tiết hủy đơn");
        }
        else
        {
            bt_cancel.setText("Hủy đơn");
        }
        bt_cancel.setOnClickListener(view ->
        {
            DsOrder.remove(order);
            order.setState("Đã hủy");
            DatabaseReference editOrder = FirebaseDatabase.getInstance()
                    .getReference("DonHang/"+InFoStatic.user.getUid()
                    +"/"+order.getId());
            editOrder.setValue(order).addOnCompleteListener(task -> {
                if(task.isSuccessful())
                {
                    notifyDataSetChanged();
                    Toast.makeText(context,"Hủy thành công",Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    @Override
    public int getItemCount()
    {
        if(DsOrder != null)
            return DsOrder.size();
        return 0;
    }

    public static class OderHolder extends RecyclerView.ViewHolder {
       ImageView image;
       TextView nameOrder, ColorAndSize, SL, money1, moneyTong, stateOrder;
       Button bt_cancel;
        public OderHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_order);
            nameOrder = itemView.findViewById(R.id.name_order);
            ColorAndSize = itemView.findViewById(R.id.colorAndSize);
            SL = itemView.findViewById(R.id.SLOrder);
            money1 = itemView.findViewById(R.id.money1);
            moneyTong = itemView.findViewById(R.id.SumMoneyOrder);
            bt_cancel = itemView.findViewById(R.id.bt_Cancel);
            stateOrder = itemView.findViewById(R.id.state_order);
        }
    }
}
