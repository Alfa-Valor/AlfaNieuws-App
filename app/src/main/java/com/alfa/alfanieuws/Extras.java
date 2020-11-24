package com.alfa.alfanieuws;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Extras {
    static boolean isConnected(Context c) {
        ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
