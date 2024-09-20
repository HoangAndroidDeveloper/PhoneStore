package com.example.phonestore.State;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class NetworkCallback extends ConnectivityManager.NetworkCallback
{
    Context context;

    public NetworkCallback(Context context) {
        this.context = context;
    }

    @Override
    public void onAvailable(@NonNull Network network) {
        super.onAvailable(network);
        Toast.makeText(context,"Internet",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUnavailable() {
        super.onUnavailable();
        Toast.makeText(context,"No Internet",Toast.LENGTH_SHORT).show();

    }
}
