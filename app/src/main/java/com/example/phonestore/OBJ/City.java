package com.example.phonestore.OBJ;

import java.util.ArrayList;
import java.util.List;

public class City
{
    int dau, cuoi;
    String city;

    public City(int dau, int cuoi, String city) {
        this.dau = dau;
        this.cuoi = cuoi;
        this.city = city;
    }

    public int getDau() {
        return dau;
    }

    public int getCuoi() {
        return cuoi;
    }

    public String getCity() {
        return city;
    }

    public static List<City> CiTy, Quan, Xa;

    public static void setCity()
    {
        CiTy = new ArrayList<>();
        CiTy.add(new City(0,0,"Hà Nội"));
        CiTy.add(new City(0,2,"Hồ Chí Minh"));
        CiTy.add(new City(0,4,"Hà Nam"));
        CiTy.add(new City(0,1,"Thanh Hóa"));
        CiTy.add(new City(0,3,"Nam Định"));

        Quan = new ArrayList<>();
        Quan.add(new City(0,0,"Hoàng mai"));
        Quan.add(new City(0,2,"Hoàn Kiếm"));
        Quan.add(new City(0,3,"Hà Đông"));
        Quan.add(new City(0,1,"Đống Đa"));
        Quan.add(new City(0,4,"Thanh Xuân"));
        Quan.add(new City(1,5,"Yên Định"));
        Quan.add(new City(1,6,"Vĩnh Lộc"));

        Xa = new ArrayList<>();
        Xa.add(new City(0,0,"Định Công"));
        Xa.add(new City(0,0,"Giáp Bát"));
        Xa.add(new City(0,0,"Lĩnh Nam"));
        Xa.add(new City(0,0,"Yên sở"));

    }

}
