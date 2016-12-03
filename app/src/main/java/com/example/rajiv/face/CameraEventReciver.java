package com.example.rajiv.face;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.kairos.Kairos;
import com.kairos.KairosListener;

public class CameraEventReciver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        final Kairos myKairos = new Kairos();
        String app_id = "ee1d1bdc";
        String api_key = "7a4709c57eb67232e6605fa33fab69dd";
        myKairos.setAuthentication(context, app_id, api_key);
        // Create an instance of the KairosListener
        final KairosListener listener = new KairosListener() {

            @Override
            public void onSuccess(String response) {
                // your code here!
                Log.d("success", response);
            }

            @Override
            public void onFail(String response) {
                // your code here!
                Log.d("fail", response);
            }
        };
        Toast.makeText(context, "New Photo Clicked", Toast.LENGTH_LONG).show();

    }
}