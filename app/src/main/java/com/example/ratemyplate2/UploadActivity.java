package com.example.ratemyplate2;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.content.Intent.ACTION_VIEW;
import static android.os.Environment.getExternalStoragePublicDirectory;

public class UploadActivity extends AppCompatActivity {

    private static final String TAG = "UploadActivity";
    static final int REQUEST_PHOTO = 1;
    private static final String EXTRA_PHOTO_FILENAME = "com.example.ratemyplate2.photo_filename";

    static private ArrayList<Plate> mPlates;
    private Button takePhotoButton;
    private Button uploadButton;
    private EditText nameText;
    private EditText captionText;
    String currentPhotoPath;
    private Plate mPlate;
    ImageView imageView;
    private Image i;
    private int check = 0;
    private int threshold = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        mPlates = PlateCollection.get(getApplication()).getPlates();
        i = new Image(EXTRA_PHOTO_FILENAME);
        mPlate = new Plate("Default", "Default", i);


        takePhotoButton = findViewById(R.id.takePhoto);
        uploadButton = findViewById(R.id.upload);
        nameText = findViewById(R.id.editNameText);
        captionText = findViewById(R.id.editCaptionText);
        imageView = findViewById(R.id.imageView);

        verifyPermissions();
        takePhoto();
        upload(uploadButton);
    }

    public String getNameText(){
        return nameText.getText().toString();

    }

    public String getCaptionText(){
        return captionText.getText().toString();
    }

    private void upload(View view) {
        View.OnClickListener uploadListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getNameText().isEmpty() || getCaptionText().isEmpty() || check < threshold){
                    Toast.makeText(UploadActivity.this,"To upload a plate, you must have took a picture and included both a name and a caption for the plate", Toast.LENGTH_SHORT).show();
                }
                else{
                    mPlate = new Plate(getNameText(),getCaptionText(), mPlate.getImage());
                    mPlates.add(mPlate);
                    openMyPlates();
                    imageView.setImageResource(R.drawable.i2symbol);
                    threshold++;
                }

            }

        };
        uploadButton.setOnClickListener(uploadListener);
    }

    private void openMyPlates(){
        Intent intent = new Intent(this, UploadedPlatesActivity.class);
        startActivity(intent);
    }

    private void verifyPermissions() {
        Log.d(TAG, "verifyPermissions: asking user for permissions");
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), permissions[0]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getApplicationContext(), permissions[1]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getApplicationContext(), permissions[2]) == PackageManager.PERMISSION_GRANTED) {

        } else {
            ActivityCompat.requestPermissions(UploadActivity.this, permissions, REQUEST_PHOTO);
        }
    }

    private void takePhoto() {
        Log.d(TAG, "takePhoto: launching intent to capture image");

        View.OnClickListener takePhotoListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        };
        takePhotoButton.setOnClickListener(takePhotoListener);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String photoFilename = null;
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //Error while creating the File

            if (photoFile != null) {
                currentPhotoPath = photoFile.getAbsolutePath();
                Uri photoURI = FileProvider.getUriForFile(this, "com.example.ratemyplate2.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_PHOTO);;
            }

        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_" ;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        i.setmFileName(image.getAbsolutePath());
        mPlate.setImage(i);
        Log.d(TAG, "createImageFile: " + i);


        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_PHOTO) {
                //Create a new Image object and attach it to imageView
                File file = new File(currentPhotoPath);
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), Uri.fromFile(file));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (bitmap != null){
                    Bitmap rotatedBm = PictureUtils.getRotatedImage(bitmap, currentPhotoPath);
                    Log.d(TAG, "onActivityResult: " + mPlate);
                    imageView.setImageBitmap(rotatedBm);
                    check++;


                }

            }
        }
    }
}



