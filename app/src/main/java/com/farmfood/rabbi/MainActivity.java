package com.farmfood.rabbi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    GridView grid;
    String[] web = {
            "Google",
            "Github",
            "Instagram",
            "Facebook",
            "Flickr",
            "Pinterest",
            "Quora",
            "Twitter",
            "Vimeo",
            "WordPress",
            "Youtube",
            "Stumbleupon",
            "SoundCloud",
            "Reddit",
            "Blogger"

    } ;
    int[] imageId = {
            R.drawable.aeroplane,
            R.drawable.badge,
            R.drawable.book,
            R.drawable.car,
            R.drawable.hammer,
            R.drawable.key,
            R.drawable.monitor,
            R.drawable.notepad,
            R.drawable.pencil,
            R.drawable.phone,
            R.drawable.search,
            R.drawable.shield,
            R.drawable.stopwatch,
            R.drawable.aeroplane,
            R.drawable.badge

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CustomGrid adapter = new CustomGrid(MainActivity.this, web, imageId);

        grid = (GridView)findViewById(R.id.grid);

        grid.setAdapter(adapter);

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();

            }
        });


    }
}
