package com.example.rajiv.face;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Environment;
import android.os.FileObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.kairos.*;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b = (Button) findViewById(R.id.Start);


        final Kairos myKairos = new Kairos();
        String app_id = "ee1d1bdc";
        String api_key = "7a4709c57eb67232e6605fa33fab69dd";
        myKairos.setAuthentication(this, app_id, api_key);
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
        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String image = "http://images.huffingtonpost.com/2014-10-12-obama.jpg";
                String subjectId = "obama";
                String galleryId = "friends";
                try {

                    myKairos.enroll(image, subjectId, galleryId, null, null, null, listener);
                    myKairos.enroll("https://upload.wikimedia.org/wikipedia/commons/9/90/PM_Modi_2015.jpg", "modi", galleryId, null, null, null, listener);
                    myKairos.enroll("https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcSCmPTWEcjEbJ3tlfWzx6pONA25xf-dX9_1zXHhieDiMTCnj20_", "mmmmmmm", galleryId, null, null, null, listener);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                image = "https://lh6.googleusercontent.com/-zL2hGAaDwmU/AAAAAAAAAAI/AAAAAAAC4-o/4p4uf0mnPDs/s0-c-k-no-ns/photo.jpg";

                try {
                    myKairos.recognize(image, galleryId, null, null, null, null, listener);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }
        });


    }
}