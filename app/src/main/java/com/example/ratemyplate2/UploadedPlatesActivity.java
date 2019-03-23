package com.example.ratemyplate2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

public class UploadedPlatesActivity extends AppCompatActivity {

    private static final String TAG = "UploadedPlatesList";

    private RecyclerViewAdapter adapter;
    private ArrayList<Plate> mPlates;
    private Plate mplate;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myplates);
        mPlates = PlateCollection.get(getApplication()).getPlates();

        Log.d(TAG, "onCreate: started");
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(mPlates, this);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onPause(){
        super.onPause();
        PlateCollection.get(getApplication()).savePlates();
    }


}
