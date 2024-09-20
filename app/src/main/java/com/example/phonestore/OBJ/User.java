package com.example.phonestore.OBJ;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class User {

    public static FirebaseUser checkLogin() // kiểm tra xem đã đăng nhập chưa
    {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

}
