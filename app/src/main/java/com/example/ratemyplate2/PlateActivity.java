package com.example.ratemyplate2;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class PlateActivity extends AppCompatActivity {
    // define a constant for TAGS
    private static final String TAG = "PlateActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plate);

        getIncomingIntent();
    }


    private void getIncomingIntent() {
        Log.d(TAG, "getIncomingIntent: checking for incoming intents");

        //checks if there is intent, stops the app from crashing if there isnt anything to get
        if(getIntent().hasExtra("image") && getIntent().hasExtra("image_name") && getIntent().hasExtra("image_caption")) {
            Log.d(TAG, "getIncomingIntent: found intent");

            // gets the image intent
            Bitmap image = getIntent().getParcelableExtra("image");
            String imageName = getIntent().getStringExtra("image_name");
            String imageCaption = getIntent().getStringExtra("image_caption");
            Log.d("images", imageName + image);
            setScreen(image, imageName, imageCaption);
        }
    }

    private void setScreen(Bitmap bM, String imageName, String imageCaption) {
        Log.d(TAG,"setImage: setting the image and to widgets");

        // sets the name view
        TextView name = findViewById(R.id.imageName);
        name.setText(imageName);

        //set the image view
        ImageView image = findViewById(R.id.imageView);
        image.setImageBitmap(bM);

        //set the caption view
        TextView caption = findViewById(R.id.imageCaption);
        caption.setText(imageCaption);
    }
}
