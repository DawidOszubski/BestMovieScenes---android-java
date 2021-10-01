package com.example.bestmoviescenes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        int[] backgroundImageId = {R.drawable.shrek, R.drawable.shrek2, R.drawable.shrek3, R.drawable.shrek4, R.drawable.asterixobelix,R.drawable.favourite };

        final ArrayList<Movie> movieArrayList = new ArrayList<>();

        for (int i =0; i< backgroundImageId.length; i++){
            Movie movie = new Movie(backgroundImageId[i]);
            movieArrayList.add(movie);
        }
        final ListView listView = findViewById(R.id.listview);
        ListAdapter listAdapter = new ListAdapter(MainActivity.this,movieArrayList);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               Intent intent = new Intent(getApplicationContext(), AudioActivity.class);
               intent.putExtra("ClickedIndex", String.valueOf(i));
                startActivity(intent);
            }
        });

    }
}