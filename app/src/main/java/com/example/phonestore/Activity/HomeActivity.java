package com.example.phonestore.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.phonestore.Fragment.ChatFragment;
import com.example.phonestore.Fragment.DonHangFragment;
import com.example.phonestore.Fragment.HomeFragment;
import com.example.phonestore.Fragment.MyFragment;
import com.example.phonestore.OBJ.DonHang;
import com.example.phonestore.OBJ.Phone;
import com.example.phonestore.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.Serializable;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView navigationView;
    public static HomeActivity h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        h = this;
        AnhXa();
        DonHang.getDsCart();
        Fragment fragment1 = new HomeFragment(this);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.layout_replace, fragment1);
        fragmentTransaction.commit();
        navigationView.setOnNavigationItemSelectedListener(menuItem ->
        {
            Fragment fragment = null;
            if (menuItem.getItemId() == R.id.Home) {
                fragment = new HomeFragment(HomeActivity.this);

            } else if (menuItem.getItemId() == R.id.Profile) {
                fragment = new MyFragment();
            } else if(menuItem.getItemId() == R.id.order) {
                fragment = new DonHangFragment();
            }
            else
            {
                fragment = new ChatFragment();
            }

            FragmentManager fragmentManager1 = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
            fragmentTransaction1.replace(R.id.layout_replace, fragment);
            fragmentTransaction1.commit();


            return true;
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.Layout_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


    }

    public void AnhXa() {
        navigationView = findViewById(R.id.bottom_navigation);
    }
public void startActivity(Phone phone)
{
    Intent it = new Intent(this, ChiTietSPActivity.class);
    Bundle bundle = new Bundle();
    bundle.putSerializable("SanPham", (Serializable) phone);
    it.putExtra("bundle",bundle);
    startActivityForResult(it,10);
}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 10 && resultCode == Activity.RESULT_OK)
        {
            Fragment fragment1 = new DonHangFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.layout_replace, fragment1);
            fragmentTransaction.commit();
        }
    }
    public void HideKeyboard()
    {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getApplicationWindowToken(), 0);
    }
}