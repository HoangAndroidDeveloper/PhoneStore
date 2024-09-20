package com.example.phonestore.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.phonestore.MyMethod.InFoStatic;
import com.example.phonestore.OBJ.KhachHang;
import com.example.phonestore.OBJ.User;
import com.example.phonestore.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChiTietAccount extends AppCompatActivity {
    TextView email, sdt, name, birthDay, sex;
    LinearLayout bt_edit, bt_forgotPass;
    ImageView btBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chitiet_account);
        AnhXa();
        getKH();
        bt_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ChiTietAccount.this, EditAccount.class);
                startActivityForResult(it, 10);
            }
        });
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        bt_forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ChiTietAccount.this, ForgotPass.class);
                startActivity(it);
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }

    private void AnhXa() {
        btBack = findViewById(R.id.back);
        bt_edit = findViewById(R.id.bt_edit);
        email = findViewById(R.id.email);
        sdt = findViewById(R.id.SDT);
        name = findViewById(R.id.Name);
        birthDay = findViewById(R.id.BirthDay);
        sex = findViewById(R.id.Sex);
        bt_forgotPass = findViewById(R.id.ly_forgot_pass);
    }

    public void getKH() // lấy thông tin khách hàng
    {
        if (InFoStatic.user == null)
            InFoStatic.user = User.checkLogin();
        KhachHang.getKH(InFoStatic.user.getUid());
        DatabaseReference getUser = FirebaseDatabase
                .getInstance().getReference("KH/" + InFoStatic.user.getUid());
        getUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                InFoStatic.kh = snapshot.getValue(KhachHang.class);
                email.setText(InFoStatic.kh.getEmail());
                if (InFoStatic.kh.getSdt() != null)
                    sdt.setText(InFoStatic.kh.getSdt());
                if (InFoStatic.kh.getBirthday() != null)
                    birthDay.setText(InFoStatic.kh.getBirthday());
                if (InFoStatic.kh.getSex() != null)
                    sex.setText(InFoStatic.kh.getSex());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}