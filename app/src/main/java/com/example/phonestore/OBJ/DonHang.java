package com.example.phonestore.OBJ;

import androidx.annotation.NonNull;

import com.example.phonestore.MyMethod.InFoStatic;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;

public class DonHang implements Serializable {
    public String id;
    public String name, ram, color, storage, image, location, state;
    public long gia, sl, slKho;

    public DonHang(String id, String name, String ram, String color, String storage, String image, String location, String state, long gia, long sl, long slKho)
    {
        this.id = id;
        this.name = name;
        this.ram = ram;
        this.color = color;
        this.storage = storage;
        this.image = image;
        this.location = location;
        this.state = state;
        this.gia = gia;
        this.sl = sl;
        this.slKho = slKho;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public DonHang() {
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

    public long getSl() {
        return sl;
    }

    public void setSl(long sl) {
        this.sl = sl;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getStorage() {
        return storage;
    }

    public long getSlKho() {
        return slKho;
    }

    public void setSlKho(long slKho) {
        this.slKho = slKho;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public long getGia() {
        return gia;
    }

    public void setGia(long gia) {
        this.gia = gia;
    }

    public static void getDsCart()
    {
        String id = "";
        if(InFoStatic.user != null)
        {
            id = InFoStatic.user.getUid();
        }
        DatabaseReference get = FirebaseDatabase.getInstance()
                .getReference("Cart/"+id);
        get.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                InFoStatic.DsCart = new ArrayList<>();
                for(DataSnapshot sn : snapshot.getChildren())
                {
                    DonHang dh = sn.getValue(DonHang.class);
                    InFoStatic.DsCart.add(dh);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
