package com.example.phonestore.API;

public class InFo { // kiểm tra thông tin

    public static boolean CheckInFo(String ...info) // kiểm tra thông tin nhập vào
    {
        for(String i : info)
        {
            if(i.isEmpty())
                return false;
        }
        return true;
    }
}
