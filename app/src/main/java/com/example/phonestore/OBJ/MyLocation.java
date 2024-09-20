package com.example.phonestore.OBJ;

import androidx.annotation.NonNull;

import com.example.phonestore.MyMethod.InFoStatic;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;

public class MyLocation implements Serializable {

     private String city, quan, xa, namekh;
     private String diachi;
     private String sdt;
     long id;
     String iduser;

     public MyLocation() {
     }

     public MyLocation(String city, String quan, String xa, String namekh
             , String diachi, String sdt, long id, String iduser)
     {
          this.city = city;
          this.quan = quan;
          this.xa = xa;
          this.namekh = namekh;
          this.diachi = diachi;
          this.sdt = sdt;
          this.id = id;
          this.iduser = iduser;
     }



     public String getCity() {
          return city;
     }

     public void setCity(String city) {
          this.city = city;
     }

     public String getQuan() {
          return quan;
     }

     public void setQuan(String quan) {
          this.quan = quan;
     }

     public String getXa() {
          return xa;
     }

     public void setXa(String xa) {
          this.xa = xa;
     }

     public String getDiachi() {
          return diachi;
     }

     public void setDiachi(String diachi) {
          this.diachi = diachi;
     }

     public String getSdt() {
          return sdt;
     }

     public void setSdt(String sdt) {
          this.sdt = sdt;
     }



     public String getNamekh() {
          return namekh;
     }

     public void setNamekh(String namekh) {
          this.namekh = namekh;
     }

     public long getId() {
          return id;
     }

     public void setId(long id) {
          this.id = id;
     }

     public String getIduser() {
          return iduser;
     }

     public void setIduser(String iduser) {
          this.iduser = iduser;
     }
     public static void getDSLocation() // lấy ds địa chỉ của khách hàng
     {
          DatabaseReference get = FirebaseDatabase.getInstance()
                  .getReference("Location/"+ InFoStatic.kh.id);
          get.addListenerForSingleValueEvent(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot snapshot)
               {
                    for(DataSnapshot sn : snapshot.getChildren())
                    {
                         MyLocation my = sn.getValue(MyLocation.class);
                         InFoStatic.DsLocation.add(my);
                    }
               }

               @Override
               public void onCancelled(@NonNull DatabaseError error) {
               }
          });
     }



}
