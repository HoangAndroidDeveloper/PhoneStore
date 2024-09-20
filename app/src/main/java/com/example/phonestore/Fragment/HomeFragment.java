package com.example.phonestore.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.phonestore.Activity.HomeActivity;
import com.example.phonestore.Activity.Search;
import com.example.phonestore.Adapter.DanhMucAD;
import com.example.phonestore.Adapter.SlideAD;
import com.example.phonestore.OBJ.Phone;
import com.example.phonestore.R;
import com.example.phonestore.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment
{

    HomeActivity h;
    public HomeFragment(HomeActivity h) {
     this.h = h;
    }

    ViewPager2 ImageSlide;
RecyclerView rDM, rSP;
FragmentHomeBinding bHome;
SearchView searchView;
List<Phone> LPhone; // ds điện thoại
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bHome = FragmentHomeBinding.inflate(inflater,container,false);
        AnhXa();
        Phone.getLPhone(LPhone,rSP);
        List<String> LImage = new ArrayList<>();
        LImage.add("https://dienthoaihay.vn/images/slideshow/2024/03/04/compress/realme-11-5g_1709517432.jpg");
        LImage.add("https://dienthoaihay.vn/images/slideshow/2024/03/22/compress/realme-q5-pro_1711100001.jpg");
        LImage.add("https://dienthoaihay.vn/images/products/categories/slideshow/2024/01/22/large/redmi-note-12-turbo_1705461426_1705887364.jpeg");
        LImage.add("https://img.tgdd.vn/imgt/f_webp,fit_outside,quality_100/https://cdn.tgdd.vn/2024/04/banner/Upgrade-iPhone-720-220--1--720x220-4.png");
        SlideAD slideAD = new SlideAD(LImage,container.getContext());
        ImageSlide.setAdapter(slideAD);
        bHome.circleSlide.setViewPager(ImageSlide);
        String [] LDM = requireContext().getResources().getStringArray(R.array.ListHang);
        DanhMucAD DMAd = new DanhMucAD(LDM,getContext());
        LinearLayoutManager ln = new LinearLayoutManager(container.getContext()
                ,RecyclerView.HORIZONTAL,false);
        rDM.setLayoutManager(ln);
        rDM.setAdapter(DMAd);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
               int i = bHome.slideImage.getCurrentItem();
               i++;
               bHome.slideImage.setCurrentItem(i % LImage.size(),true);
               handler.postDelayed(this,3000);
            }
        },3000);
        search();
        return bHome.getRoot();
    }
    public void AnhXa()
    {
        ImageSlide = bHome.slideImage;
        rDM = bHome.rDanhMuc;
        rSP = bHome.rSP;
        LPhone = new ArrayList<>();
        searchView = bHome.eSearch;
    }
    public void search() // nhấn tìm kiếm
    {
       searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
       {
           @Override
           public boolean onQueryTextSubmit(String query)
           {
               Intent it = new Intent(getContext(), Search.class);
               it.putExtra("keyword",query);
               startActivityForResult(it,10);
               return false;
           }

           @Override
           public boolean onQueryTextChange(String newText) {
               return false;
           }
       });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 10 && resultCode == Activity.RESULT_OK)
        {
           h.HideKeyboard();
        }
    }
}