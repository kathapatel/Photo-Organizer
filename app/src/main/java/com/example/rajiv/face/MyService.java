package com.example.rajiv.face;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.FileObserver;
import android.os.IBinder;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class MyService extends Service {
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Let it continue running until it is stopped.
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();

        final String pathToWatch = android.os.Environment.getExternalStorageDirectory().toString() + "/DCIM/Camera/";
        Log.d("test", pathToWatch);
        FileObserver observer = new FileObserver(pathToWatch) { // set up a file observer to watch this directory on sd card

            @Override
            public void onEvent(int event, String file) {
                //if(event == FileObserver.CREATE && !file.equals(".probe")){ // check if its a "create" and not equal to .probe because thats created every time camera is launched

                Log.d("test", event + "");
                Log.d("test", "File created [" + pathToWatch + file + "]" + event);
                try {
                    if (!pathToWatch.substring(pathToWatch.lastIndexOf('/')).equals("null") && event==FileObserver.CREATE) {
                        Log.d("test", "now it is right time");

                        File sd = Environment.getExternalStorageDirectory();
                        File image = new File(sd+pathToWatch);
                        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                        Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(), bmOptions);

                        //Bitmap bm = BitmapFactory.decodeFile(pathToWatch);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
                        byte[] b = baos.toByteArray();
                        String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
                        Log.d("test", encodedImage + event);
                    }
                } catch (Exception e) {
                    Log.d("test", e.toString() + "error");
                }
                //}
            }
        };
        observer.startWatching(); //START OBSERVING
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }
}