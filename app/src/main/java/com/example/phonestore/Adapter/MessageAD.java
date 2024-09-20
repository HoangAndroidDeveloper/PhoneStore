package com.example.phonestore.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonestore.OBJ.Message;
import com.example.phonestore.R;

import java.util.List;
import java.util.Objects;


public class MessageAD extends RecyclerView.Adapter<MessageAD.MessHolder>
{
   List<Message> LMess;
   Context context;
   String id; // id này để phân biệt tn nhận hay gửi

    public MessageAD(List<Message> LMess, Context context, String id)
    {
        this.LMess = LMess;
        this.context = context;
    }

    @NonNull
    @Override
    public MessHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_messenge,parent,false);
        return new MessHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessHolder holder
            , @SuppressLint("RecyclerView") int position)
    {
        Message message = LMess.get(position);
        String mess = message.mess;
        String idSend = message.idsend;
        String time = message.time;
        CardView imageDen = holder.imageDen;
        LinearLayout lyDen = holder.lyDen;
        LinearLayout lyDi = holder.lyDi;
        if(!Objects.equals("0",idSend))
        {
            holder.timeDi.setText(time);
            holder.tnDi.setText(mess);
            lyDen.setVisibility(View.GONE);
            imageDen.setVisibility(View.GONE);
        }
        else
        {
            holder.timeDen.setText(time);
            holder.tnDen.setText(mess);
            lyDi.setVisibility(View.GONE);
        }
    }




    @Override
    public int getItemCount()
    {
        if(LMess == null)
         return 0;
        return LMess.size();
    }




    public static class MessHolder extends RecyclerView.ViewHolder
    {
        TextView tnDen, tnDi, timeDen, timeDi;
        LinearLayout lyDen, lyDi;
        CardView imageDen;
        public MessHolder(@NonNull View itemView)
        {
            super(itemView);
            tnDen = itemView.findViewById(R.id.tn_den);
            tnDi = itemView.findViewById(R.id.tn_di);
            timeDi = itemView.findViewById(R.id.time_di);
            timeDen = itemView.findViewById(R.id.time_den);
            lyDen = itemView.findViewById(R.id.layout_den);
            lyDi = itemView.findViewById(R.id.layout_di);
            imageDen = itemView.findViewById(R.id.card_avt_den);
        }
    }
}
