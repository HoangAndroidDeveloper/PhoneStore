package com.example.phonestore.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonestore.Adapter.DatHangAdapter;
import com.example.phonestore.Adapter.LocationAdapter;
import com.example.phonestore.Helper.AppInfo;
import com.example.phonestore.Helper.CreateOrder;
import com.example.phonestore.MyMethod.InFoStatic;
import com.example.phonestore.MyMethod.MyClass;
import com.example.phonestore.OBJ.DonHang;
import com.example.phonestore.OBJ.MyLocation;
import com.example.phonestore.OBJ.User;
import com.example.phonestore.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class ThanhToan extends AppCompatActivity {

    RadioButton tra_sau, Zalopay;
    ImageView btBack;
    TextView sumMoneySP, sumMoney2, bt_addLocation, locationDefault;
    RecyclerView rDsDatHang;
    DatHangAdapter adapter;
    Button btDatHang;
    ConstraintLayout layout;
    long sumMoney;
    LinearLayout linearLayout;
    LinearLayout linearLayout1;
    MyLocation myLocation;
    List<DonHang> LDonHang;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_thanh_toan);
        AnhXa();
        user = FirebaseAuth.getInstance().getCurrentUser();
        locationDefault.setOnClickListener(view -> {
            Intent it = new Intent(ThanhToan.this, DsLocation.class);
            startActivityForResult(it, 1);
        });
        getDSLocation();
        // get ds đặt hàng
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            LDonHang = (List<DonHang>) bundle.getSerializable("DonHang");
            setData();
        }
        // set rDatHang
        adapter = new DatHangAdapter(LDonHang, this);
        LinearLayoutManager ln = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rDsDatHang.setLayoutManager(ln);
        rDsDatHang.setAdapter(adapter);

        // chuyển qua activity DsLocation
        linearLayout1.setOnClickListener(view ->
        {
            Intent it = new Intent(ThanhToan.this, DsLocation.class);
            startActivityForResult(it, 1);
        });
        // click bt đặt hàng
        btDatHang.setOnClickListener(view ->
        {
            String location = locationDefault.getText().toString();
            if (location.isEmpty())
            {
                Toast.makeText(ThanhToan.this, "Chưa có địa chỉ giao hàng"
                        , Toast.LENGTH_SHORT).show();
            }
            else
            {
                if(Zalopay.isChecked())
                {
                    payZalo();
                }
                else if(tra_sau.isChecked())
                {
                    putDonHang();
                }
                else
                {
                    Toast.makeText(ThanhToan.this,"Chưa chọn phương thưc thanh toán",Toast.LENGTH_SHORT).show();
                }
            }

        });
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void AnhXa() {
        btBack = findViewById(R.id.bt_back_Mua);
        rDsDatHang = findViewById(R.id.Ds);
        sumMoneySP = findViewById(R.id.sumMoneyMua_3);
        sumMoney2 = findViewById(R.id.sumMoneyMua_2);
        btDatHang = findViewById(R.id.bt_DatHang);
        layout = findViewById(R.id.layout);
        bt_addLocation = findViewById(R.id.bt_startActivityLocation);
        linearLayout = findViewById(R.id.LnLayout);
        locationDefault = findViewById(R.id.Location_default);
        linearLayout1 = findViewById(R.id.LnLayout1);
        tra_sau = findViewById(R.id.tra_sau);
        Zalopay = findViewById(R.id.zaloPay);
    }

    public void setData()
    {
        long money = 0;
        for (DonHang donHang : LDonHang) {
            money = money + donHang.gia * donHang.sl;
        }
        sumMoney2.setText(MyClass.FormatMoney(money));
        sumMoneySP.setText(MyClass.FormatMoney(money + 20000));
        sumMoney = money + 20000;
    }

    public void getDSLocation() // lấy ds địa chỉ của khách hàng
    {
        DatabaseReference get = FirebaseDatabase.getInstance()
                .getReference("Location/" + user.getUid());
        get.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                InFoStatic.DsLocation = new ArrayList<>();
                int i = 1;
                for (DataSnapshot sn : snapshot.getChildren()) {

                    MyLocation my = sn.getValue(MyLocation.class);
                    InFoStatic.DsLocation.add(my);
                    if (i == 1) {
                        i++;
                        linearLayout.setVisibility(View.VISIBLE);
                        locationDefault.setVisibility(View.VISIBLE);
                        linearLayout1.setVisibility(View.GONE);
                        assert my != null;
                        locationDefault.setText(my.getDiachi() + ", " + my.getXa()
                                + ", " + my.getQuan() + ", " + my.getCity());
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            if (!InFoStatic.DsLocation.isEmpty()) {
                MyLocation my = InFoStatic.DsLocation.get(LocationAdapter.index);
                locationDefault.setText(my.getDiachi() + ", " + my.getXa()
                        + ", " + my.getQuan() + ", " + my.getCity());
                locationDefault.setVisibility(View.VISIBLE);
                linearLayout.setVisibility(View.VISIBLE);
                linearLayout1.setVisibility(View.GONE);
            } else {
                locationDefault.setVisibility(View.GONE);
                linearLayout.setVisibility(View.GONE);
                linearLayout1.setVisibility(View.VISIBLE);
            }
        }
    }

    public void payZalo()
    {
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ZaloPaySDK.init(AppInfo.APP_ID, Environment.SANDBOX);
        CreateOrder orderApi = new CreateOrder();
        try {
            String amount = sumMoney + "";
            JSONObject data = orderApi.createOrder(amount);
            String code = data.getString("return_code");
            if (code.equals("1")) {
                String token = data.getString("zp_trans_token");
                // demozpdk là: sau khi thanh toán xong thì trở về app của mình
                ZaloPaySDK.getInstance().payOrder(this, token, "demozpdk://app", new PayOrderListener()
                {
                    @Override
                    public void onPaymentSucceeded(final String transactionId, final String transToken, final String appTransID)
                    {

                        Toast.makeText(ThanhToan.this, "Thanh toán thành công", Toast.LENGTH_SHORT).show();
                        putDonHang();
                    }

                    @Override
                    public void onPaymentCanceled(String zpTransToken, String appTransID) {
                        Toast.makeText(ThanhToan.this, "Thanh toán bị hủy", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPaymentError(ZaloPayError zaloPayError, String zpTransToken, String appTransID) {
                        Toast.makeText(ThanhToan.this, "Thanh toán thất bại", Toast.LENGTH_SHORT).show();
                    }

                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);
        ZaloPaySDK.getInstance().onResult(intent);
    }

    private void putDonHang()
    {
        int i = 0;
        String location = locationDefault.getText().toString();
        for (DonHang donHang : LDonHang)
        {
            donHang.location = location;
            donHang.id = donHang.id + i;
            donHang.state = "Đang chuẩn bị hàng";
            i++;
            if(InFoStatic.user == null)
                InFoStatic.user = User.checkLogin();
            DatabaseReference put = FirebaseDatabase.getInstance()
                    .getReference("DonHang/"+user.getUid()+"/"+donHang.id);
            put.setValue(donHang);

        }
        Toast.makeText(ThanhToan.this,"Đặt hàng thành công",Toast.LENGTH_SHORT).show();
        setResult(Activity.RESULT_OK);
        finish();
    }

}