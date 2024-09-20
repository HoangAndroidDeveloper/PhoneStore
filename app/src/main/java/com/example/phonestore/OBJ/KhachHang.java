package com.example.phonestore.OBJ;

import androidx.annotation.NonNull;

import com.example.phonestore.MyMethod.InFoStatic;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class KhachHang
{
    String id;
    String name,sdt, birthday, sex, email, image, pass;

    public KhachHang()
    {

    }

    public KhachHang(String id, String name, String sdt, String birthday, String sex, String email, String image, String pass) {
        this.id = id;
        this.name = name;
        this.sdt = sdt;
        this.birthday = birthday;
        this.sex = sex;
        this.email = email;
        this.image = image;
        this.pass = pass;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public static void getKH(String id) // lấy thông tin khách hàng
    {
        DatabaseReference getUser = FirebaseDatabase
                .getInstance().getReference("KH/"+id);
         getUser.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot snapshot)
             {
                 InFoStatic.kh = snapshot.getValue(KhachHang.class);
             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {

             }
         });
    }



}
