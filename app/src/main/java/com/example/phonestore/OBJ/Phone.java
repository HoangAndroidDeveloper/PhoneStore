package com.example.phonestore.OBJ;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonestore.Activity.HomeActivity;
import com.example.phonestore.Adapter.SP_AD;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.List;

public class Phone implements Serializable {
    public long id, daban, star;
    public String name, hang, mota;
    public List<String> image; // màu, ram, storage
    public long gia, soluong;
    public List<Version> version;

    public Phone() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHang() {
        return hang;
    }

    public void setHang(String hang) {
        this.hang = hang;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }


    public Phone(long id, long daban, long star, String name, String hang
            , String mota, List<String> image, long gia, long soluong
            , List<Version> version) {
        this.id = id;
        this.daban = daban;
        this.star = star;
        this.name = name;
        this.hang = hang;
        this.mota = mota;
        this.image = image;
        this.gia = gia;
        this.soluong = soluong;
        this.version = version;
    }

    public long getDaban() {
        return daban;
    }

    public void setDaban(long daban) {
        this.daban = daban;
    }

    public long getStar() {
        return star;
    }

    public void setStar(long star) {
        this.star = star;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<String> getImage() {
        return image;
    }

    public void setImage(List<String> image) {
        this.image = image;
    }

    public long getGia() {
        return gia;
    }

    public void setGia(long gia) {
        this.gia = gia;
    }

    public long getSoluong() {
        return soluong;
    }

    public void setSoluong(long soluong) {
        this.soluong = soluong;
    }

    public List<Version> getVersion() {
        return version;
    }

    public void setVersion(List<Version> version) {
        this.version = version;
    }



    public static class Version implements Serializable {
        public Version() {
        }

        public Version(String color, String ram, String storage, long gia, long sl) {
            this.color = color;
            this.ram = ram;
            this.storage = storage;
            this.gia = gia;
            this.sl = sl;
        }

        public String color, ram, storage;
        public long gia, sl;

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getRam() {
            return ram;
        }

        public void setRam(String ram) {
            this.ram = ram;
        }

        public String getStorage() {
            return storage;
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

        public long getSl() {
            return sl;
        }

        public void setSl(long sl) {
            this.sl = sl;
        }
    }

    public static void getLPhone(List<Phone> LPhone, RecyclerView rSP)// get danh sách điện thoại
    {
        DatabaseReference get = FirebaseDatabase.getInstance()
                .getReference("SanPham");
        SP_AD.SPClick spClick = new SP_AD.SPClick() {
            @Override
            public void clickSP(Phone phone) {
                HomeActivity.h.startActivity(phone);
            }
        };
        get.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot sn : snapshot.getChildren()) {
                    Phone phone = sn.getValue(Phone.class);
                    LPhone.add(phone);
                }
                SP_AD spAd = new SP_AD(rSP.getContext(), LPhone, spClick);

                GridLayoutManager gridLayoutManager = new GridLayoutManager(rSP.getContext()
                        , 2);
                rSP.setLayoutManager(gridLayoutManager);
                rSP.setAdapter(spAd);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
