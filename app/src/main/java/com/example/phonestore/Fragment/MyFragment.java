package com.example.phonestore.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.phonestore.Activity.ChiTietAccount;
import com.example.phonestore.LoginActivity;
import com.example.phonestore.MyMethod.InFoStatic;
import com.example.phonestore.OBJ.KhachHang;
import com.example.phonestore.OBJ.User;
import com.example.phonestore.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyFragment extends Fragment {

LinearLayout bt_info, bt_logout;
ImageView avatar;
View view;
    public MyFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my, container, false);
        bt_info = view.findViewById(R.id.bt_info);
        bt_logout = view.findViewById(R.id.bt_Logout);
        bt_logout.setOnClickListener(v -> {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            startActivity(new Intent(getContext(), LoginActivity.class));
            requireActivity().finish();
            auth.signOut();
        });
        if(InFoStatic.user == null)
            InFoStatic.user = User.checkLogin();
        String id = InFoStatic.user.getUid();
        avatar = view.findViewById(R.id.avatar);
        getKh(id);

        bt_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getContext(), ChiTietAccount.class);
                startActivity(it);
            }
        });
        return view;
    }
    public void getKh(String id)
    {
        DatabaseReference get = FirebaseDatabase.getInstance()
                .getReference("KH/"+id);
        get.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                InFoStatic.kh = snapshot.getValue(KhachHang.class);
                Glide.with(view.getContext()).load(InFoStatic.kh.getImage()).into(avatar);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}