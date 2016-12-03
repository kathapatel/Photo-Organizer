package com.example.rajiv.face;

import android.content.Intent;
import android.os.FileObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class Test1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);
//        final String pathToWatch = android.os.Environment.getExternalStorageDirectory().toString() + "/DCIM/Camera/";
//        Log.d("test",pathToWatch);
//        FileObserver observer = new FileObserver(pathToWatch) { // set up a file observer to watch this directory on sd card
//
//            @Override
//            public void onEvent(int event, String file) {
//                //if(event == FileObserver.CREATE && !file.equals(".probe")){ // check if its a "create" and not equal to .probe because thats created every time camera is launched
//                Log.d("test1",event+"");
//                Log.d("test", "File created [" + pathToWatch + file + "]"+ event);
//                //}
//            }
//        };
//        observer.startWatching(); //START OBSERVING
    }
    public void startService(View view) {
        startService(new Intent(getBaseContext(), MyService.class));
    }

    // Method to stop the service
    public void stopService(View view) {
        stopService(new Intent(getBaseContext(), MyService.class));
    }
}
