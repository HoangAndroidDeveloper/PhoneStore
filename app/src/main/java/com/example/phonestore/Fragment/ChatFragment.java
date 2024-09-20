package com.example.phonestore.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonestore.Adapter.MessageAD;
import com.example.phonestore.MyMethod.InFoStatic;
import com.example.phonestore.OBJ.Message;
import com.example.phonestore.OBJ.User;
import com.example.phonestore.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


public class ChatFragment extends Fragment
{



    public ChatFragment()
    {

    }

RecyclerView rMess;
EditText ip_mess;
    List<Message> LMessage;
    Context context;
    ImageButton bt_send;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
         view =  inflater.inflate(R.layout.fragment_chat, container, false);
         AnhXa();
         if(InFoStatic.user == null)
          InFoStatic.user = User.checkLogin();
         rMess.setLayoutManager(new LinearLayoutManager(context,RecyclerView.VERTICAL
                 ,false));
         getMessage();
         bt_send.setOnClickListener(v -> {
             sendMess();

    });
         return view;
    }
    public  void AnhXa()
    {
        context = getContext();
        ip_mess = view.findViewById(R.id.ip_message);
        bt_send = view.findViewById(R.id.bt_send);
        rMess = view.findViewById(R.id.rMessage);
    }
    public void sendMess()
    {
        HideKeyboard();
        String mess = ip_mess.getText().toString().trim();
        if(!mess.isEmpty())
        {
            ip_mess.setText("");
            String id = InFoStatic.user.getUid();
            Date currentDate = new Date();

            // In thời gian hiện tại theo định dạng tùy chỉnh
            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
            String time =  formatter.format(currentDate);
            Message message = new Message(id,"0",mess,time);
            LMessage.add(message);
            DatabaseReference put = FirebaseDatabase.getInstance()
                    .getReference("Message/" + id);
            put.setValue(LMessage);
        }
    }

    public void getMessage()
    {
        LMessage = new ArrayList<>();
        String id = InFoStatic.user.getUid();
        DatabaseReference get = FirebaseDatabase.getInstance()
                .getReference("Message/"+id);
        get.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                LMessage.clear();
               for(DataSnapshot sn : snapshot.getChildren())
               {
                   Message mess = sn.getValue(Message.class);
                   LMessage.add(mess);
               }
                MessageAD messageAD = new MessageAD(LMessage,context,id);
                rMess.setAdapter(messageAD);
                rMess.post(() -> rMess.scrollToPosition(Objects.requireNonNull(rMess.getAdapter()).getItemCount() - 1));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });
    }
public void HideKeyboard()
{
    InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
    inputMethodManager.hideSoftInputFromWindow(ip_mess.getApplicationWindowToken(),0);
}



}