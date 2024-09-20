package com.example.phonestore.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonestore.Adapter.LocationAdapter;
import com.example.phonestore.MyMethod.InFoStatic;
import com.example.phonestore.R;

public class DsLocation extends AppCompatActivity
{
    TextView bt_add;
    Button bt_selectLocation;
    RecyclerView rDsLocation;
    ImageView btBack;
    LocationAdapter adapter;
    LocationAdapter.LocationClick locationClick;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ds_location);
        AnhXa();
        // set rLocation
         locationClick = new LocationAdapter.LocationClick() {
             @Override
             public void clickEdit() {
                 Intent it = new Intent(DsLocation.this, AddLocation.class);
                 it.putExtra("index", LocationAdapter.index);
                 startActivityForResult(it,10);
             }
         };
        adapter = new LocationAdapter(InFoStatic.DsLocation,this,locationClick);
        LinearLayoutManager ln = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        rDsLocation.setLayoutManager(ln);
        rDsLocation.setAdapter(adapter);

        // click bt thêm location
        bt_add.setOnClickListener(view -> {
            Intent it = new Intent(DsLocation.this, AddLocation.class);
            it.putExtra("index",-1);
            startActivityForResult(it, 10);
        });

        bt_selectLocation.setOnClickListener(view -> {
            if(InFoStatic.DsLocation.isEmpty())
            {
                Toast.makeText(DsLocation.this, "Chưa có địa chỉ vui lòng thêm địa chỉ mới", Toast.LENGTH_SHORT).show();
            }
            else
            {
                setResult(Activity.RESULT_OK, new Intent());
                finish();
            }
        });
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ly_DsLocation), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void AnhXa() {
        btBack = findViewById(R.id.bt_back_DsLocation);
        rDsLocation = findViewById(R.id.rDsLocation);
        bt_add = findViewById(R.id.bt_addLocation);
        bt_selectLocation = findViewById(R.id.bt_selectLocation);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 10 && resultCode == Activity.RESULT_OK)
        {
            adapter = new LocationAdapter(InFoStatic.DsLocation,this,locationClick);
            rDsLocation.setAdapter(adapter);
        }
    }

    @Override
    public void onBackPressed()
    {
        setResult(Activity.RESULT_OK, new Intent());
        finish();
        super.onBackPressed();
    }

}