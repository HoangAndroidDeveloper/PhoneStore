package com.example.phonestore.Activity;


import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.phonestore.API.InFo;
import com.example.phonestore.OBJ.KhachHang;
import com.example.phonestore.R;
import com.example.phonestore.databinding.ActivityDangKyBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class DangKyActivity extends AppCompatActivity {
    TextInputLayout tEmail, t_pass1, t_name, t_pass2;
    TextInputEditText eEmail, ePass1, ePass2, eName;
    AppCompatButton bt_Sign;
    ActivityDangKyBinding dkBinding;
    String email = "", name = "", pass1 = "", pass2 = "";
    Dialog dialog;
    private DatabaseReference putUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        dkBinding = ActivityDangKyBinding.inflate(getLayoutInflater());
        setContentView(dkBinding.getRoot());
        AnhXa();
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_loading_dialog);
        Window window = dialog.getWindow();
        assert window != null;
        dialog.setCancelable(false);
        window.setBackgroundDrawableResource(R.color.transparent);
        setErrorInfo();
        SignAcc();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void AnhXa() {
        t_name = dkBinding.layoutEName;
        t_pass1 = dkBinding.layoutEPass;
        t_pass2 = dkBinding.layoutEPass2;
        tEmail = dkBinding.layoutEEmail;
        eEmail = dkBinding.eEmail;
        ePass1 = dkBinding.ePass;
        ePass2 = dkBinding.ePass2;
        eName = dkBinding.eName;
        bt_Sign = dkBinding.btLogin;
    }

    public void setErrorInfo()// chưa nhập thông tin thì sẽ hiện viền đỏ
    {
        eName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().isEmpty()) {
                    t_name.setError("Không được để trống");
                } else {
                    name = charSequence.toString();
                    t_name.setError("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ePass2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().isEmpty()) {
                    t_pass2.setError("Không được để trống");
                } else {
                    pass2 = charSequence.toString();
                    t_pass2.setError("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ePass1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().isEmpty()) {
                    t_pass1.setError("Không được để trống");
                } else {
                    pass1 = charSequence.toString();
                    t_pass1.setError("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        eEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().isEmpty()) {
                    tEmail.setError("Không được để trống");
                } else {
                    email = charSequence.toString();
                    tEmail.setError("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void SignAcc() // nhấn đăng ký và lưu thông tin khách hàng lên firebase
    {
        bt_Sign.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                if (InFo.CheckInFo(name, pass1, pass2, email))
                {
                    if (Objects.equals(pass1, pass2)) {

                        email = email.trim();
                        pass1 = pass1.trim();
                        dialog.show();
                        dialog.setCancelable(false);
                        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                        firebaseAuth.createUserWithEmailAndPassword(email, pass1)
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {

                                        putUser(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid());
                                        firebaseAuth.signOut();

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        dialog.dismiss();
                                        if (Objects.equals(e.getMessage(), "The email address is badly formatted."))
                                            Toast.makeText(DangKyActivity.this, "Email không đúng định dạng"
                                                    , Toast.LENGTH_SHORT).show();
                                        else if (Objects.equals(e.getMessage(), "The given password is invalid. [ Password should be at least 6 characters ]")) {
                                            Toast.makeText(DangKyActivity.this, "Mật khẩu phải có ít nhất 6 ký tự"
                                                    , Toast.LENGTH_SHORT).show();
                                        } else if (Objects.equals(e.getMessage(), "The email address is already in use by another account.")) {
                                            Toast.makeText(DangKyActivity.this, "Địa chỉ email đã được sử dụng bởi một tài khoản khác."
                                                    , Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    } else
                        Toast.makeText(DangKyActivity.this, "Mật khẩu không trùng nhau"
                                , Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DangKyActivity.this, "Vui lòng nhập đầy đủ thông tin"
                            , Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    public void putUser(String id) // put thông tin khách hàng lên firebase
    {

        putUser = FirebaseDatabase.getInstance().getReference("KH/" + id);
        KhachHang kh = new KhachHang(id, name, "", "", "", email, null, pass1);
        putUser.setValue(kh).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    dialog.dismiss();
                    onBackPressed();
                    Toast.makeText(DangKyActivity.this, "Đăng ký thành công"
                            , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}