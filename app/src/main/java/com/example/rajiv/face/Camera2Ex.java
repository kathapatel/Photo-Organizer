package com.example.rajiv.face;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.os.FileObserver;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.kairos.Kairos;
import com.kairos.KairosListener;

import org.json.JSONException;

import java.io.File;
import java.io.UnsupportedEncodingException;

public class Camera2Ex extends AppCompatActivity {
    public String path = "sdcard/camera_app/";
    public String filename = "cam_image.jpg";
    Button button;
    ImageView imageView;
    FileObserver observer;
    static final int CAM_REQUEST = 1;
    final Kairos myKairos = new Kairos();
    String app_id = "ee1d1bdc";
    String api_key = "7a4709c57eb67232e6605fa33fab69dd";
    public KairosListener listener;
    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this.getApplicationContext();
        setContentView(R.layout.activity_camera2_ex);
        button = (Button) findViewById(R.id.btncapture);
        imageView = (ImageView) findViewById(R.id.image_view);
        myKairos.setAuthentication(this, app_id, api_key);
        listener = new KairosListener() {

            @Override
            public void onSuccess(String response) {

                Log.d("KAIROS DEMO", response);
                Log.d("tl-1", response);
            }

            @Override
            public void onFail(String response) {
                Log.d("KAIROS DEMO", response);

                Log.d("tl-1", response + "check");
            }
        };

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File file = getFile();
                camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                startActivityForResult(camera_intent, CAM_REQUEST);
/*String subjectId = "obama";*/
            }
        });

        observer = new FileObserver(path) { // set up a file observer to watch this directory on sd card

            @Override
            public void onEvent(int event, String file) {

                if (event == FileObserver.CREATE && !file.equals(".probe")) { // check if its a "create" and not equal to .probe because thats created every time camera is launched


                    //////////////////////////////////////////////////////////////////////////
                    //Toast.makeText(getBaseContext(), file + " was saved!", Toast.LENGTH_LONG).show();
                }
            }
        };
        observer.startWatching(); //START OBSERVING
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        String path = "sdcard/camera_app/" + filename;
        ///////////////////////////////////////////////////////////////////////
        String galleryId = "friends";
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);

//                BitmapFactory.Options options = new BitmapFactory.Options();
//                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
//                Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        Log.d("tl-1", "captured");
//                    Toast.makeText(context, "captured", Toast.LENGTH_LONG).show();
        String selector = "FULL";
        String threshold = "0.75";
        String minHeadScale = "0.25";
        String maxNumResults = "25";
        try {
            myKairos.recognize(bitmap,
                    galleryId,
                    selector,
                    threshold,
                    minHeadScale,
                    maxNumResults,
                    listener);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("tl-1", e.toString());
//                        Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
        } catch (UnsupportedEncodingException e) {
            Log.d("tl-1", e.toString());
            e.printStackTrace();
//                        Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
        }
        imageView.setImageDrawable(Drawable.createFromPath(path));
    }

    private File getFile() {
        File folder = new File("sdcard/camera_app");
        if (!folder.exists()) {
            folder.mkdir();
        }

        Long tsLong = System.currentTimeMillis() / 1000;
        String ts = tsLong.toString();
        File image_file = new File(folder, "cam_image.jpg" + ts);
        filename = "cam_image.jpg" + ts;
        return image_file;
    }

}

