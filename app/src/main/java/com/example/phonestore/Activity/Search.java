package com.example.phonestore.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonestore.Adapter.SP_AD;
import com.example.phonestore.OBJ.Phone;
import com.example.phonestore.R;
import com.example.phonestore.databinding.ActivitySearchBinding;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Search extends AppCompatActivity
{
ActivitySearchBinding binding;
AutoCompleteTextView Gia;
TextInputLayout boxGia;
TextView text;
RecyclerView rPhone;
Context context;
SearchView searchView;
List<Phone> LPhone;
    SP_AD spAd;

    SP_AD.SPClick spClick ;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AnhXa();
        setAutoText();
        GridLayoutManager manager = new GridLayoutManager(this,2);
        rPhone.setLayoutManager(manager);
        String keyword = getIntent().getStringExtra("keyword");
        assert keyword != null;
        keyword = keyword.toLowerCase();
        getData(keyword);
        SapXepSP();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                getData(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ly_search), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void AnhXa()
    {
        boxGia = binding.boxGia;
        Gia = binding.selectGia;
       rPhone = binding.rPhone;
       searchView = binding.eSearch;
       text = binding.text;
    }
    public void setAutoText()
    {
        String[] DsGia = getResources().getStringArray(R.array.LGia);
        ArrayAdapter<String> adGia = new ArrayAdapter<>(this
                , R.layout.item_auto_text, DsGia);
        Gia.setAdapter(adGia);
        Gia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed()
    {
        setResult(Activity.RESULT_OK);
        finish();
    }

    public void getData(String key)
    {
         spClick = new SP_AD.SPClick() {
            @Override
            public void clickSP(Phone phone)
            {
                HomeActivity.h.startActivity(phone);
            }
        };
        LPhone = new ArrayList<>();
        DatabaseReference get = FirebaseDatabase.getInstance()
                .getReference("SanPham");
        get.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot sn : snapshot.getChildren())
                {
                    Phone phone = sn.getValue(Phone.class);
                    if(phone != null)
                    {
                        String name = phone.getName().toLowerCase();
                        String hang = phone.hang.toLowerCase();
                        String name1 = "Điện thoại".toLowerCase();
                        String name2 = "Smart phone".toLowerCase();
                        String name3 = "Phone".toLowerCase();
                        if (name.contains(key) || hang.contains(key) || name2.contains(key)
                            || name1.contains(key) || name3.contains(key))
                        {
                        LPhone.add(phone);
                        }

                    }
                }
                 spAd = new SP_AD(Search.this,LPhone,spClick);
                rPhone.setAdapter(spAd);
                if(LPhone.isEmpty())
                {
                    text.setVisibility(View.VISIBLE);
                    boxGia.setVisibility(View.GONE);
                }
                else
                {
                    text.setVisibility(View.GONE);
                    boxGia.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void SapXepSP() // Sắp xếp sp theo giá
    {
        Gia.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String gia = Gia.getText().toString();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    if (gia.equals("Thấp đến cao"))
                        LPhone.sort((Phone p1, Phone p2) -> {
                            if (p1.getGia() > p2.getGia())
                                return 1;
                            else
                                return -1;
                        });
                    else {
                        LPhone.sort((Phone p1, Phone p2) -> {
                            if (p1.getGia() > p2.getGia())
                                return -1;
                            else
                                return 1;
                        });
                    }
                }

                spAd.LPhone = LPhone;
                rPhone.setAdapter(spAd);
            }


        });
    }

}