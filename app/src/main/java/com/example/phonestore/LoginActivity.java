package com.example.phonestore;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.phonestore.API.InFo;
import com.example.phonestore.Activity.DangKyActivity;
import com.example.phonestore.Activity.HomeActivity;
import com.example.phonestore.MyMethod.InFoStatic;
import com.example.phonestore.OBJ.KhachHang;
import com.example.phonestore.OBJ.User;
import com.example.phonestore.databinding.ActivityMainBinding;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    ActivityMainBinding bLogin;
    AppCompatButton btLogin;
    TextInputLayout tEmail, tPass;
    TextInputEditText eEmail, ePass;
    String pass = "";
    TextView bt_forgotPass;
    FirebaseUser user;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        bLogin = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(bLogin.getRoot());
        AnhXa();
        setError();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        if (user != null) {
            InFoStatic.user = user;
            KhachHang.getKH(user.getUid());
            Intent it = new Intent(this, HomeActivity.class);
            startActivity(it);
            finish();
        }
        btLogin.setOnClickListener(view ->
        {
            login();
        });
        bLogin.btNextDK.setOnClickListener(view -> {

            Intent it = new Intent(LoginActivity.this, DangKyActivity.class);
            startActivity(it);
        });

        bt_forgotPass.setOnClickListener(v ->
        {
            FirebaseAuth auth1 = FirebaseAuth.getInstance();
            String emailAddress = Objects.requireNonNull(eEmail.getText()).toString().trim();
            if(emailAddress.isEmpty())
            {
                Toast.makeText(LoginActivity.this,"Vui lòng nhập email",Toast.LENGTH_SHORT).show();

            }
            else {
                auth1.sendPasswordResetEmail(emailAddress)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Đã gửi link đặt lại mặt khẩu vào tới email", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    public void AnhXa()
    {
        tEmail = bLogin.layoutEEmail;
        tPass = bLogin.layoutEPassword;
        eEmail = bLogin.eEmail;
        ePass = bLogin.ePass;
        btLogin = bLogin.btLogin;
        bt_forgotPass = bLogin.btForgotPass;
    }

    public void setError() {
        ePass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() <= 8)
                    pass = charSequence.toString();
                if (pass.isEmpty()) {
                    tPass.setError("Không được để trống");
                } else if (charSequence.toString().length() > 8) {
                    tPass.setError("Không được quá 8 ký tự");
                    ePass.setText(pass);
                } else {
                    tPass.setError("");
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
                    tEmail.setError("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void login()
    {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_loading_dialog);
        Window window = dialog.getWindow();
        dialog.setCancelable(false);
        assert window != null;
        window.setBackgroundDrawableResource(R.color.transparent);
        String email = Objects.requireNonNull(eEmail.getText()).toString().trim();
        String pass = Objects.requireNonNull(ePass.getText()).toString().trim();
        if (InFo.CheckInFo(email, pass))
        {
            dialog.show();
            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Intent it = new Intent(LoginActivity.this, HomeActivity.class);
                    InFoStatic.user = User.checkLogin();
                    startActivity(it);
                    finish();
                    dialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công"
                            , Toast.LENGTH_SHORT).show();
                } else {
                    dialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Sai email hoặc mật khẩu"
                            , Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(LoginActivity.this, "Chưa nhập tài đủ thông tin tài khoản"
                    , Toast.LENGTH_SHORT).show();


        }
    }
}