package com.example.phonestore.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.phonestore.MyMethod.InFoStatic;
import com.example.phonestore.MyMethod.MyClass;
import com.example.phonestore.OBJ.KhachHang;
import com.example.phonestore.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.util.List;
import java.util.Objects;

import gun0912.tedimagepicker.builder.TedImagePicker;
import gun0912.tedimagepicker.builder.listener.OnSelectedListener;

public class EditAccount extends AppCompatActivity {
    EditText ip_name, ip_phone;
    ImageView image, btBack;
    CheckBox cNam, cNu;
    TextView BirthDay;
    AppCompatButton bt_save;
    String uriImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_account);
        PermissionListener permissionlistener = new PermissionListener()
        {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(EditAccount.this, "Yes", Toast.LENGTH_SHORT).show();
                TedImagePicker.with(EditAccount.this)
                        .start(new OnSelectedListener() {
                            @Override
                            public void onSelected(@NonNull Uri uri) {
                                uriImage = String.valueOf(uri);
                                image.setImageURI(uri);
                            }
                        });
            }

            @Override
            public void onPermissionDenied(List<String> list) {

            }
        };
        AnhXa();
        KhachHang kh = InFoStatic.kh;
        ip_name.setText(kh.getName());
        if (kh.getSdt() != null)
            ip_phone.setText(kh.getSdt());
        if (kh.getBirthday() != null)
            BirthDay.setText(kh.getBirthday());
        if(kh.getImage() != null)
        {
            Glide.with(this).load(kh.getImage()).into(image);
            uriImage = kh.getImage();
        }
        image.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                TedPermission.create()
                        .setPermissionListener(permissionlistener)
                        .setPermissions(android.Manifest.permission.CAMERA
                                , Manifest.permission.READ_EXTERNAL_STORAGE)
                        .check();
            }
        });
        cNam.setOnClickListener(v -> {
            if(cNu.isChecked())
            {
                cNam.setChecked(true);
                cNu.setChecked(false);
            }
            else
            {

                cNam.setChecked(true);
            }

        });
        cNu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cNam.isChecked())
                {
                    cNu.setChecked(true);
                    cNam.setChecked(false);
                }
                else
                {
                    cNu.setChecked(true);
                }
            }
        });
        clickBirth();
        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveInfo();
            }
        });
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ly_editAcc), (v, insets) -> {
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
        btBack = findViewById(R.id.Back);
        ip_name = findViewById(R.id.ip_name);
        ip_phone = findViewById(R.id.ip_phone);
        cNam = findViewById(R.id.c_nam);
        cNu = findViewById(R.id.c_nu);
        BirthDay = findViewById(R.id.ip_birthDay);
        image = findViewById(R.id.image_acc);
        bt_save = findViewById(R.id.bt_save);
    }
   public void clickBirth()
   {
       @SuppressLint("SetTextI18n") DatePickerDialog.OnDateSetListener listener = (view, year, month, dayOfMonth) -> {
           BirthDay.setText(dayOfMonth+"/"+month+"/"+year);
       };
       DatePickerDialog datePickerDialog = new DatePickerDialog(this
               , android.R.style.Theme_Holo_Light_Dialog_MinWidth
       ,listener,4,5,6);

      Objects.requireNonNull(datePickerDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
       BirthDay.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
              datePickerDialog.show();
           }
       });
   }
   public void SaveInfo() // lưu thông tin đã chỉnh sửa
   {
       String name = ip_name.getText().toString();
       String sdt = ip_phone.getText().toString();
       String sex ;
       if(cNu.isChecked())
        sex = cNu.getText().toString();
       else
       {
           sex = cNam.getText().toString();
       }
       String birth = BirthDay.getText().toString();
       InFoStatic.kh = new KhachHang(InFoStatic.user.getUid(),name,sdt,birth,sex,InFoStatic.user.getEmail()
       ,uriImage,InFoStatic.kh.getPass());
      if(MyClass.checkInfo(name,sdt,sex,birth,uriImage))
      {
          DatabaseReference put = FirebaseDatabase.getInstance()
                  .getReference("KH/"+InFoStatic.user.getUid());
          put.setValue(InFoStatic.kh).addOnCompleteListener(new OnCompleteListener<Void>() {
              @Override
              public void onComplete(@NonNull Task<Void> task) {
                  if(task.isSuccessful())
                  {
                      Toast.makeText(EditAccount.this,"Đã lưu",Toast.LENGTH_SHORT).show();
                      setResult(Activity.RESULT_OK);
                      finish();
                  }
              }
          });
      }
      else
      {
          Toast.makeText(this,"Chưa nhập đủ thông tin",Toast.LENGTH_SHORT).show();
      }
   }
}