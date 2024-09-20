package com.example.phonestore.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonestore.Adapter.OrderAdapter;
import com.example.phonestore.MyMethod.InFoStatic;
import com.example.phonestore.OBJ.DonHang;
import com.example.phonestore.OBJ.User;
import com.example.phonestore.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class DonHangFragment extends Fragment
{
    TabLayout tabLayout;
    List<DonHang> DsOrder;
    RecyclerView rOrder;

    public DonHangFragment() {

    }

    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_don_hang, container, false);
        AnhXa();
        tabLayout.addTab(tabLayout.newTab().setText("Đang chuẩn bị hàng"));
        tabLayout.addTab(tabLayout.newTab().setText("Đã đóng gói"));
        tabLayout.addTab(tabLayout.newTab().setText("Đã giao cho shiper"));
        tabLayout.addTab(tabLayout.newTab().setText("Đang vận chuyển"));
        tabLayout.addTab(tabLayout.newTab().setText("Đã giao"));
        tabLayout.addTab(tabLayout.newTab().setText("Đã hủy"));
        getDonHang("Đang chuẩn bị hàng");
        rOrder.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        tabSelect();
        return view;
    }

    private void AnhXa()
    {
        DsOrder = new ArrayList<>();
        tabLayout = view.findViewById(R.id.tabLayout_State_Order);
        rOrder = view.findViewById(R.id.rOrder);
    }
    public void tabSelect()
    {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                getDonHang(Objects.requireNonNull(tab.getText()).toString());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    public void getDonHang(String state)
    {
        if(InFoStatic.user == null)
        {
            InFoStatic.user = User.checkLogin();
        }
        DatabaseReference get = FirebaseDatabase.getInstance()
                .getReference("DonHang/"+InFoStatic.user.getUid());
        get.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                DsOrder = new ArrayList<>();
                for(DataSnapshot sn : snapshot.getChildren())
                {
                    DonHang dh = sn.getValue(DonHang.class);
                    String trangThai = dh.getState();
                    if(Objects.equals(trangThai,state))
                    {
                        DsOrder.add(dh);
                    }
                }
                OrderAdapter orderAdapter = new OrderAdapter(DsOrder,getContext());
                rOrder.setAdapter(orderAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}