package com.example.phonestore.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.phonestore.MyMethod.InFoStatic;
import com.example.phonestore.MyMethod.MyClass;
import com.example.phonestore.OBJ.MyLocation;
import com.example.phonestore.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class AddLocation extends AppCompatActivity {

    EditText  DiaChi;
    TextInputEditText Phone, inputName;
    TextView  selectCity, selectQuan, selectXa;
    private static final int  rCode = 1;
    DatabaseReference put ;
    ImageView btBack;
    String diaChi, City, Quan ,Xa ,sdt, name;

    Button btXacNhan;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_location);
        AnhXa();
        int index = getIntent().getIntExtra("index",0);
        if(index >= 0)
        {

            DiaChi.setText(InFoStatic.DsLocation.get(index).getDiachi());
            selectCity.setText(InFoStatic.DsLocation.get(index).getCity());
            selectQuan.setText(InFoStatic.DsLocation.get(index).getQuan());
            selectXa.setText(InFoStatic.DsLocation.get(index).getXa());
            inputName.setText(InFoStatic.DsLocation.get(index).getNamekh());
            Phone.setText(InFoStatic.DsLocation.get(index).getSdt());
        }
        selectCity.setOnClickListener(view -> {
            Intent it = new Intent(AddLocation.this,SelectLocation.class);
            startActivityForResult(it,rCode);
        });
        btXacNhan.setOnClickListener(view -> {
            diaChi = DiaChi.getText().toString();
            City = selectCity.getText().toString();
            Quan = selectQuan.getText().toString();
            Xa = selectXa.getText().toString();
            sdt = Objects.requireNonNull(Phone.getText()).toString();
            name = Objects.requireNonNull(inputName.getText()).toString();

                if (MyClass.checkInfo(diaChi, City, Quan, Xa, sdt, name))
                {

                    if(index < 0)
                    {
                        putLocation();
                    }
                    else
                    {
                        long id = InFoStatic.DsLocation.get(index).getId();
                        String idUser = InFoStatic.DsLocation.get(index).getIduser();
                        MyLocation location = new MyLocation(City,Quan,Xa,name,diaChi,sdt,id,idUser);
                        InFoStatic.DsLocation.set(index,location);
                        put = FirebaseDatabase.getInstance().getReference("Location/"+idUser);
                        put.setValue(InFoStatic.DsLocation);
                        setResult(Activity.RESULT_OK, new Intent());
                        finish();
                    }
                }
                else {
                    Toast.makeText(AddLocation.this, "Chưa nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }


        });
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ly_AddLocation), (v, insets) -> {
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
        inputName = findViewById(R.id.input_name);
        selectCity = findViewById(R.id.selectCity);
        selectQuan = findViewById(R.id.SelectQuan);
        selectXa = findViewById(R.id.SelectXa);
        btXacNhan = findViewById(R.id.bt_xacNhan);
        DiaChi = findViewById(R.id.inputLocation);
        Phone = findViewById(R.id.input_PhoneNumber);

    }
    public void putLocation()
    {

        MyLocation location = new MyLocation(City,Quan,Xa,name,diaChi,sdt, MyClass.createId()
                , InFoStatic.kh.getId());
        InFoStatic.DsLocation.add(location);
        put = FirebaseDatabase.getInstance().getReference("Location/"+location.getIduser());
        put.setValue(InFoStatic.DsLocation).addOnCompleteListener(task -> {
            if(task.isSuccessful())
            {
                setResult(Activity.RESULT_OK, new Intent());
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == rCode && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                String city = data.getStringExtra("city");
                String quan = data.getStringExtra("quan");
                String xa = data.getStringExtra("xa");
                Toast.makeText(this, quan + xa, Toast.LENGTH_LONG).show();
                selectCity.setText(city);
                selectXa.setText(xa);
                selectQuan.setText(quan);
            } else {
                Toast.makeText(this, "No", Toast.LENGTH_LONG).show();

            }
        }
    }

}
