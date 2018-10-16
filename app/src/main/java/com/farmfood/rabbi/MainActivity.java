package com.farmfood.rabbi;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button add;
    DatabaseReference databaseUser;
    List<DataInput> userlist;
    GridView gridView;
    ProgressBar mprogress;

    @Override
    protected void onCreate(Bundle savedInstanceState)

    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userlist = new ArrayList<>();

        mprogress =(ProgressBar) findViewById(R.id.mprogressbar);
        add =(Button)findViewById(R.id.addatabtn);

        gridView = (GridView) findViewById(R.id.listv);

        databaseUser =FirebaseDatabase.getInstance().getReference("users");


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ib = new Intent(getApplicationContext(), DataInputActivity.class);
                startActivity(ib);
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                DataInput datas=userlist.get(position);
                customDialog(datas.getUsername(),datas.getPrice(),datas.getUserdescription(),datas.getMimageUrl());

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
                        Collections.reverse(userlist);
                    }
                    CustomGrid adapter = new CustomGrid(MainActivity.this, userlist);
                    gridView.setAdapter(adapter);
                    mprogress.setVisibility(View.INVISIBLE);
                } catch (Exception e)
                {
                    Log.i("exception is", String.valueOf(e));
                }

            }

            @Override
            public void onCancelled( DatabaseError databaseError) {
                Toast.makeText(MainActivity.this,databaseError.getMessage(),Toast.LENGTH_LONG).show();
                mprogress.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void customDialog(String name, String price,String desc,String imagurl)
    {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.profiledilog);
        TextView dname = (TextView) dialog.findViewById(R.id.dialogname);
        TextView ddescrip = (TextView) dialog.findViewById(R.id.dialogdescription);
        TextView dprice = (TextView) dialog.findViewById(R.id.dialogprice);
        ImageView dimage=(ImageView) dialog.findViewById(R.id.dialogimage);
        Button dsave=(Button) dialog.findViewById(R.id.savebtn);
        Button dcancel=(Button) dialog.findViewById(R.id.cancelbtn);
        CardView cardv= (CardView) dialog.findViewById(R.id.cardview);
        cardv.setRadius(30);
        cardv.setCardElevation(30);
        cardv.setContentPadding(5,5,5,5);

        dname.setText(name);
        ddescrip.setText(desc);
        dprice.setText(price);
        Picasso.with(this)
                .load(imagurl)
                .fit()
                .placeholder(R.drawable.ic_launcher_background)
                .centerCrop()
                .noFade()
                .into(dimage);

        dsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        dcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();

    }



}
