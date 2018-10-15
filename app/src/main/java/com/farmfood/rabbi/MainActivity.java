package com.farmfood.rabbi;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button add;
    DatabaseReference databaseUser;
    List<DataInput> userlist;
    GridView listV;

    @Override
    protected void onCreate(Bundle savedInstanceState)

    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userlist = new ArrayList<>();

        add =(Button)findViewById(R.id.addatabtn);

        listV = (GridView) findViewById(R.id.listv);

        databaseUser =FirebaseDatabase.getInstance().getReference("users");


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ib = new Intent(getApplicationContext(), DataInputActivity.class);
                startActivity(ib);
            }
        });


    }

    @Override
    protected void onStart()
    {
        super.onStart();

        databaseUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    userlist.clear();
                    for (DataSnapshot usersnapshot: dataSnapshot.getChildren())
                    {
                        DataInput usersdata = usersnapshot.getValue(DataInput.class);
                        userlist.add(usersdata);

                    }
                    CustomGrid adapter = new CustomGrid(MainActivity.this, userlist);
                    listV.setAdapter(adapter);
                } catch (Exception e)
                {
                    Log.i("exception is", String.valueOf(e));
                }

            }

            @Override
            public void onCancelled( DatabaseError databaseError) {
                Toast.makeText(MainActivity.this,databaseError.getMessage(),Toast.LENGTH_LONG).show();

            }
        });
    }



}
