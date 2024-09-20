package com.example.phonestore.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

import com.example.phonestore.Adapter.CartAdapter;
import com.example.phonestore.MyMethod.InFoStatic;
import com.example.phonestore.OBJ.DonHang;
import com.example.phonestore.OBJ.User;
import com.example.phonestore.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity
{
    List<DonHang> DsCart;
    Button btMua;

    CheckBox SelectAll;
    RecyclerView recyclerCart;
    ImageView bt_backCart;
    CartAdapter cartAdapter;
    TextView btDelete;

    List<DonHang> DsItemSelect;
    @SuppressLint({"MissingInflatedId", "NotifyDataSetChanged"})
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);
        AnhXa();
        DsItemSelect = new ArrayList<>();
        DsCart = InFoStatic.DsCart;
        CartAdapter.ProcessCheckBox click = (checked, position) ->
        {
           if(checked)
           {
              DsItemSelect.add(DsCart.get(position));
              SelectAll.setChecked(DsItemSelect.size() == DsCart.size());
           }
           else
           {
               SelectAll.setChecked(false);
               CartAdapter.check = 0;
               DsItemSelect.remove(DsCart.get(position));
           }
           if(DsItemSelect.isEmpty())
               btDelete.setVisibility(View.GONE);
           else
           {
               btDelete.setVisibility(View.VISIBLE);
           }
        };
        // bt mua hàng
        btMua.setOnClickListener(view -> {
            if(DsItemSelect.isEmpty())
            {
                Toast.makeText(CartActivity.this,"Chưa chọn sản phẩm nào"
                        ,Toast.LENGTH_SHORT).show();
            }
            else
            {
                Intent it = new Intent(CartActivity.this,ThanhToan.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("DonHang", (Serializable) DsItemSelect);
                it.putExtras(bundle);
                startActivityForResult(it,10);
            }
        });
        cartAdapter = new CartAdapter(DsCart,this,click);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerCart.setLayoutManager(linearLayoutManager);
        recyclerCart.setAdapter(cartAdapter);
        SelectAll.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                boolean isChecked = SelectAll.isChecked();
            if(isChecked)
            {
                CartAdapter.check = 1;
                recyclerCart.setAdapter(cartAdapter);
            }
            else
            {
                CartAdapter.check = 0;
                recyclerCart.setAdapter(cartAdapter);
            }

        }
        });
        btDelete.setOnClickListener(view -> {
            List<DonHang> Select = DsItemSelect;
          for (DonHang cart : DsItemSelect)
          {
              DsCart.remove(cart);
              if(DsItemSelect.size() == 1)
                  DsItemSelect.clear();
          }
          DsItemSelect = Select;
          if(InFoStatic.user == null)
              InFoStatic.user = User.checkLogin();
            DatabaseReference put = FirebaseDatabase.getInstance()
                    .getReference("Cart/"+InFoStatic.user.getUid());
          put.setValue(DsCart).addOnCompleteListener(task -> {
               if(task.isSuccessful())
              {
                  if(DsCart.isEmpty())
                  {
                      btDelete.setVisibility(View.GONE);
                      SelectAll.setChecked(false);
                  }
                  cartAdapter = new CartAdapter(DsCart,CartActivity.this,click);
                  recyclerCart.setAdapter(cartAdapter);
              }
          });
        });
        bt_backCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id. ly_cart), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void AnhXa()
    {
        bt_backCart = findViewById(R.id.bt_back_cart);
        recyclerCart = findViewById(R.id.recycler_Cart);
        SelectAll = findViewById(R.id.SelectAllCart);
        btMua = findViewById(R.id.bt_Mua_Cart);
        btDelete = findViewById(R.id.bt_Delete);
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