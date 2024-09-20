package com.example.phonestore.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.phonestore.Adapter.ImageAD;
import com.example.phonestore.Adapter.ItemColor;
import com.example.phonestore.Adapter.itemRam;
import com.example.phonestore.MyMethod.InFoStatic;
import com.example.phonestore.MyMethod.MyClass;
import com.example.phonestore.OBJ.DonHang;
import com.example.phonestore.OBJ.Phone;
import com.example.phonestore.R;
import com.example.phonestore.databinding.ActivityChiTietSpactivityBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ChiTietSPActivity extends AppCompatActivity
{
    RecyclerView rColor, rRam;
    BottomSheetDialog dialogMua;
    LinearLayout ly_color, ly_ram;
    ImageView btBack;
    Phone phone;
    ViewPager2 vImage;
    ItemColor colorAD;
    itemRam ramAD;
    CardView bt_cart;
    ActivityChiTietSpactivityBinding binding;
    List<String> LColor, LRam;
    TabLayout tabLayout;
    TextView nameSP, gia, DaBan, MoTa;
    String color, ram;
    LinearLayout bt_add_cart;
    TextView bt_mua;
    Phone.Version version;
    TextView nameDialog, ramDialog, giaDialog, KhoDialog, slDialog, colorDialog;
    ImageView bt_giam, bt_tang, imageDialog;
    AppCompatButton bt_MuaDialog;
    DonHang donHang = new DonHang();
    int isCheckSelect = 0; // dùng để kiểm tra xem đã chọn đủ phiên bản chưa

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = ActivityChiTietSpactivityBinding.inflate(getLayoutInflater());
        // lấy phone từ homeFragment gửi qua
        Bundle bundle = getIntent().getBundleExtra("bundle");
        assert bundle != null;
        phone = (Phone) bundle.getSerializable("SanPham");
        AnhXa();
        MoTa.setText(phone.getMota());
        setDialog();
        donHang = new DonHang();
        donHang.name = phone.getName();
        donHang.slKho = phone.getSoluong();
        donHang.gia = phone.getGia();
        donHang.sl = 1;
        donHang.image = phone.getImage().get(0);
        bt_add_cart.setEnabled(false);
        bt_add_cart.setOnClickListener(view -> {

        });
        // ẩn màu và ram nếu không có
        if(phone.getVersion() == null)
        {
            ly_ram.setVisibility(View.GONE);
            ly_color.setVisibility(View.GONE);
            bt_add_cart.setEnabled(true);
            bt_mua.setEnabled(true);
            bt_add_cart.setBackgroundColor(getResources().getColor(R.color.bt_add_cart_select));
            bt_mua.setBackgroundColor(getResources().getColor(R.color.bt_mua_select));
        }
        bt_muaCLick();
        TangGiamSLMua();
        setData();
        getL(LColor);
        // set item tab layout
        tabLayout.addTab(tabLayout.newTab().setText("Mô tả"));
        tabLayout.addTab(tabLayout.newTab().setText("Đánh giá"));

        // set rColor
        ItemColor.colorClick ramClick = new ItemColor.colorClick() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(String color, int position)
            {
                ram = color;
                isCheckSelect++;
                if(isCheckSelect > 1)
                {
                    bt_add_cart.setEnabled(true);
                    bt_mua.setEnabled(true);
                    bt_add_cart.setBackgroundColor(getResources().getColor(R.color.bt_add_cart_select));
                    long money = getGiaVersion();
                    gia.setText( MyClass.FormatMoney(money));
                    donHang.gia = money;
                    donHang.ram = version.ram;
                    donHang.storage = version.storage;
                    donHang.slKho = version.getSl();
                    bt_mua.setBackgroundColor(getResources().getColor(R.color.bt_mua_select));
                }
                ramAD.isSelected(position);
            }
        };
        LinearLayoutManager ln1 = new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
        rColor.setLayoutManager(ln1);
        ItemColor.colorClick colorClick = (color, position) -> {
            if(position != colorAD.position)
                isCheckSelect = 0;
            this.color = color;
             isCheckSelect++;
            if(isCheckSelect > 1)
            {

                    gia.setText(MyClass.FormatMoney(getGiaVersion()));
                    if(version != null)
                    {
                        donHang.slKho = version.getSl();
                    }
            }
            else
            {
                bt_add_cart.setEnabled(false);
                bt_mua.setEnabled(false);
                bt_add_cart.setBackgroundColor(getResources().getColor(R.color.bt_add_cart_unselect));
                bt_mua.setBackgroundColor(getResources().getColor(R.color.bt_mua_unselect));
            }
            donHang.color = color;
            colorAD.isSelected(position);
            getLRam(color);
            ly_ram.setVisibility(View.VISIBLE);
            ramAD = new itemRam(LRam,ramClick);
            rRam.setAdapter(ramAD);
        };
        colorAD = new ItemColor(LColor,colorClick);
        rColor.setAdapter(colorAD);
        LinearLayoutManager ln2 = new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
        rRam.setLayoutManager(ln2);
        clickAddCart();
        bt_cart.setOnClickListener(view -> {
            Intent it = new Intent(ChiTietSPActivity.this,CartActivity.class);
            startActivityForResult(it,10);
        });

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setContentView(binding.getRoot());
    }
    public void AnhXa()
    {
        btBack = binding.btBackShop;
        MoTa = binding.MoTa;
        rColor = binding.rColor;
        rRam = binding.rRam;
        LColor = new ArrayList<>();
        LRam = new ArrayList<>();
        tabLayout = binding.tab;
        nameSP = binding.nameSP;
        gia = binding.moneySp;
        DaBan = binding.SLDaban;
        vImage = binding.ListImageSP;
        ly_color = binding.lyColor;
        ly_ram = binding.lyRam;
        bt_add_cart = binding.btAddCart;
        bt_mua = binding.btMua;
        bt_cart = binding.btGioHangShop;
    }
    public void getL( List<String> LColor) // lấy ds màu từ phone để gửi cho adapter
    {
        if(phone.getVersion() != null) {
            Map<String, String> DS = new LinkedHashMap<>();
            for (Phone.Version version : phone.getVersion()) {
                DS.put(version.color, "a");
            }
            LColor.addAll(DS.keySet());
        }
    }
    @SuppressLint("SetTextI18n")
    public void setData() // set dữ liệu cho các view
    {
        nameSP.setText(phone.getName());
        gia.setText(MyClass.FormatMoney(phone.gia));
        DaBan.setText("Đã bán "+phone.daban);
        ImageAD imageAD = new ImageAD(phone.getImage(),this);
        vImage.setAdapter(imageAD);
    }
    public void getLRam(String color) // lấy ds ram
    {
        if(!LRam.isEmpty())
            LRam.clear();
        for (Phone.Version version : phone.getVersion())
        {
            String c = version.color;
            if(Objects.equals(c,color))
            {
                LRam.add(version.getRam()+"-"+version.getStorage());
            }
        }
    }
    public long getGiaVersion() // lấy giá của các version khác nhau
    {
        if (color != null && ram != null) {
            for (Phone.Version version : phone.getVersion()) {
                String c = version.color;
                String r = version.ram + "-" + version.storage;
                if (Objects.equals(color, c) && Objects.equals(r, ram))
                {
                    this.version = version;
                    return version.gia;
                }
            }
        }
            return 0;

    }

    @SuppressLint("SetTextI18n")
    public void bt_muaCLick() // xử lý khi click vào button mua
    {
        String name = phone.getName();
        KhoDialog.setText("Kho: "+phone.getSoluong());
        nameDialog.setText(name);
        giaDialog.setText(MyClass.FormatMoney(donHang.gia));
        bt_mua.setOnClickListener(view -> {
            dialogMua.show();
            if(phone.getVersion() != null)
            {
                KhoDialog.setText("Kho: "+donHang.slKho);
                ramDialog.setText("PB: "+donHang.ram+"GB-"+donHang.storage+"GB");
                giaDialog.setText(MyClass.FormatMoney(donHang.gia));
                colorDialog.setText("Màu sắc: "+ donHang.color);
            }
            else
            {
                colorDialog.setVisibility(View.GONE);
                ramDialog.setVisibility(View.GONE);
            }
            bt_MuaDialog.setOnClickListener(view1 -> {
                Intent it = new Intent(ChiTietSPActivity.this,ThanhToan.class);
                Bundle bundle  = new Bundle();
               // donHang.image = phone.getImage().get(0);
                List<DonHang> LDonHang = new ArrayList<>();
                donHang.state = "Đang chuẩn bị hàng";
                donHang.id = MyClass.createStrId();
                LDonHang.add(donHang);
                bundle.putSerializable("DonHang", (Serializable) LDonHang);
                it.putExtras(bundle);
                startActivityForResult(it,10);
            });

        });
    }
    public void setDialog()
    {
        dialogMua = new BottomSheetDialog(ChiTietSPActivity.this);
        dialogMua.setContentView(R.layout.dialog_mua);
        imageDialog = dialogMua.findViewById(R.id.imageSP_dialog);
        Glide.with(this).load(phone.getImage().get(0)).into(imageDialog);
        nameDialog = dialogMua.findViewById(R.id.name_dialog);
        ramDialog = dialogMua.findViewById(R.id.ram_dialog);
        KhoDialog = dialogMua.findViewById(R.id.sl_kho_dialog);
        giaDialog = dialogMua.findViewById(R.id.gia_dialog);
        bt_giam = dialogMua.findViewById(R.id.bt_giam);
        bt_tang = dialogMua.findViewById(R.id.bt_tang);
        slDialog = dialogMua.findViewById(R.id.sl_dialog);
        bt_MuaDialog = dialogMua.findViewById(R.id.bt_mua_sheet);
        colorDialog = dialogMua.findViewById(R.id.color_dialog);
    }
    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    public void TangGiamSLMua() // tăng giảm số lượng sản phẩm mua
    {

        bt_tang.setOnClickListener(view -> {
            int slKho = (int) donHang.slKho;
            int sl = Integer.parseInt(slDialog.getText().toString());

            if(sl < slKho)
            {
                sl ++;
                slDialog.setText(sl + "");
                donHang.sl = sl;
                if(sl == slKho)
                 bt_tang.setImageDrawable(getResources().getDrawable(R.drawable.ic_plus_unselect30));
            }
            if(sl == 2)
            {
                bt_giam.setImageDrawable(getResources().getDrawable(R.drawable.ic_subtraction_30));
            }
        });
        bt_giam.setOnClickListener(view -> {
            int slKho = (int) donHang.slKho;
            int number = Integer.parseInt(slDialog.getText().toString());
            number--;
            if(number == slKho-1)
                bt_tang.setImageDrawable(getResources().getDrawable(R.drawable.ic_plus_30));
            if(number >= 1)
            {
                donHang.sl = number;
                slDialog.setText("" + number);
            }
            if(number == 1)
            {
                bt_giam.setImageDrawable(getResources().getDrawable(R.drawable.ic_subtraction_30_unselect));
            }
        });
    }
    @SuppressLint("SuspiciousIndentation")
    public void clickAddCart()
    {
        bt_add_cart.setOnClickListener(view -> {
        if (InFoStatic.DsCart == null)
        {
            InFoStatic.DsCart = new ArrayList<>();
        }

            donHang.sl = 1;
            donHang.id = MyClass.createStrId();
            InFoStatic.DsCart.add(donHang);
        if(InFoStatic.user == null)
        {
            InFoStatic.user = FirebaseAuth.getInstance().getCurrentUser();
        }
            DatabaseReference put = FirebaseDatabase.getInstance()
                    .getReference("Cart/" + InFoStatic.user.getUid());

            put.setValue(InFoStatic.DsCart).addOnCompleteListener(
                    task -> Toast.makeText(ChiTietSPActivity.this, "Thêm giỏ hàng thành công"
                            , Toast.LENGTH_SHORT).show());
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 10 && resultCode == Activity.RESULT_OK)
        {
            setResult(Activity.RESULT_OK);
            finish();
        }
    }
}