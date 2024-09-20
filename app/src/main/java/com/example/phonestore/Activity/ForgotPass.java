package com.example.phonestore.Activity;

import android.os.Bundle;
import android.view.View;
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

import com.example.phonestore.MyMethod.InFoStatic;
import com.example.phonestore.MyMethod.MyClass;
import com.example.phonestore.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class ForgotPass extends AppCompatActivity {
    TextView eEmail;
    ImageView btBack;
    TextInputEditText ipPass1, ipPass2, ipCode;
    AppCompatButton bt_forgot;
    String checkCode;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_pass);
        AnhXa();
        checkCode = MyClass.VerificationEmail(InFoStatic.user.getEmail());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ly_forgot_pass), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
btBack.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        onBackPressed();
    }
});
        bt_forgot.setOnClickListener(v -> {
            String pass1 = Objects.requireNonNull(ipPass1.getText()).toString().trim();
            String pass2 = Objects.requireNonNull(ipPass2.getText()).toString().trim();
            String code = Objects.requireNonNull(ipCode.getText()).toString().trim();
            if (!MyClass.checkInfo(pass1, pass2, code))
            {
                Toast.makeText(ForgotPass.this, "Chưa nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            } else {
                if (!Objects.equals(pass1, pass2)) {
                    Toast.makeText(ForgotPass.this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                } else if (!code.equals(checkCode)) {
                    Toast.makeText(ForgotPass.this, "Nhập sai mã xác thực", Toast.LENGTH_SHORT).show();
                } else {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    assert user != null;
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(Objects.requireNonNull(InFoStatic.kh.getEmail()),InFoStatic.kh.getPass())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful())
                                    {
                                        user.updatePassword(pass1)
                                                .addOnCompleteListener(task1 -> {
                                                    if (task1.isSuccessful()) {
                                                        onBackPressed();
                                                        Toast.makeText(ForgotPass.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                                    }
                                                }).addOnFailureListener(e -> Toast.makeText(ForgotPass.this, e.getMessage(), Toast.LENGTH_SHORT).show());
                                    }
                                }
                            });


                }
            }
        });
    }


    private void AnhXa() {
        btBack = findViewById(R.id.bt_back_Mua);
        eEmail = findViewById(R.id.eEmail);
        ipPass1 = findViewById(R.id.ePass1);
        ipPass2 = findViewById(R.id.ePass2);
        ipCode = findViewById(R.id.eCode);
        bt_forgot = findViewById(R.id.bt_forgot);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}