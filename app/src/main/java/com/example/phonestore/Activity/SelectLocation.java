package com.example.phonestore.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonestore.Adapter.CityAdapter;
import com.example.phonestore.OBJ.City;
import com.example.phonestore.R;
import com.google.android.material.tabs.TabLayout;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SelectLocation extends AppCompatActivity {
    TabLayout tabLayout;
    RecyclerView rCity;
    ImageView btBack;
    CityAdapter adapter;
    LinearLayout bt_getCurrentLocation;
    CityAdapter.LocationClick locationClick;
    public static int indexTab = 0; // vtri hiện tại của tab

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_select_location);
        indexTab = 0;
        AnhXa();
        tabLayout.addTab(tabLayout.newTab().setText("Thành Phố"));
        tabLayout.addTab(tabLayout.newTab().setText("Quận/ Huyện"));
        tabLayout.addTab(tabLayout.newTab().setText("Phường/ Xã"));
        // set adapter
        City.setCity();
        setAdapter();
        tabSelect();
         // tạo danh sách các tỉnh, huyện, xã
        // tắt select tab

         Objects.requireNonNull(tabLayout.getTabAt(1)).view.setEnabled(false);
         Objects.requireNonNull(tabLayout.getTabAt(2)).view.setEnabled(false);
         btBack.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 onBackPressed();
             }
         });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ly_selectLocation), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void AnhXa() {
        btBack = findViewById(R.id.bt_back_addLocation);
        tabLayout = findViewById(R.id.tab_City);
        rCity = findViewById(R.id.rDsCity);
        bt_getCurrentLocation = findViewById(R.id.getMyLocation);
    }
    public void setAdapter()
    {

        locationClick = new CityAdapter.LocationClick() {
            @Override
            public void back(String city, String quan, String xa)
            {
                Intent it = new Intent();
                it.putExtra("city",city);
                it.putExtra("quan",quan);
                it.putExtra("xa",xa);
                Toast.makeText(SelectLocation.this,city+quan+xa,Toast.LENGTH_LONG).show();
                setResult(Activity.RESULT_OK, it);
                finish();
            }

            @Override
            public void updateLocation(int id)
            {
                if(indexTab == 2)
                {
                    Objects.requireNonNull(tabLayout.getTabAt(1)).view.setEnabled(true);
                }

              tabLayout.selectTab(tabLayout.getTabAt(indexTab));
              adapter = new CityAdapter(FilterCity(id), SelectLocation.this
                      ,tabLayout,this);
              rCity.setAdapter(adapter);
            }
        };
        adapter = new CityAdapter(City.CiTy, this,tabLayout,locationClick);
        rCity.setLayoutManager(new LinearLayoutManager(this));
        rCity.setAdapter(adapter);
        rCity.addItemDecoration(new StickyRecyclerHeadersDecoration(adapter));
    }

public List<City> FilterCity(int id) // lọc quận, xã phù hợp với người mẫu
{
    List<City> city = new ArrayList<>();
    if(indexTab == 1)
    {

        for(int i = 0; i<City.Quan.size();i++)
        {
            int dau = City.Quan.get(i).getDau();
            if(dau == id)
            {
                city.add(City.Quan.get(i));
            }
        }
    }
    else
    {
        for(int i = 0; i<City.Xa.size();i++)
        {
            int dau = City.Xa.get(i).getDau();
            if(dau == id)
            {
                city.add(City.Xa.get(i));
            }
        }
    }
    return city;
}
public void tabSelect()
{
    tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
           int index = tab.getPosition();
           if(index == 0)
           {
               indexTab = 0;
               Objects.requireNonNull(tabLayout.getTabAt(1)).view.setEnabled(false);
               adapter = new CityAdapter(City.CiTy, SelectLocation.this
                       ,tabLayout,locationClick);
               rCity.setAdapter(adapter);
           }
           else if(index == 1)
           {
               indexTab = 1;
               adapter = new CityAdapter(FilterCity(CityAdapter.dauCity), SelectLocation.this
                       ,tabLayout,locationClick);
               rCity.setAdapter(adapter);
           }
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    });
}

}