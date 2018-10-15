package com.farmfood.rabbi;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DataInputActivity extends AppCompatActivity {
   private Button addata,addimage;
   private EditText getname, getdesc, pricetext;
   private DatabaseReference databaseUser;
   private StorageReference mStorageref;
   private ImageView imgv;

    private static final int PICK_IMAGE_REQUEST=1;
    private Uri mImageUri;

    private StorageTask uploadTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_input);

        databaseUser =FirebaseDatabase.getInstance().getReference("users");
        mStorageref =FirebaseStorage.getInstance().getReference("users");

        addata =(Button) findViewById(R.id.addbutton);
        getname=(EditText) findViewById(R.id.getname);
        getdesc=(EditText) findViewById(R.id.getdes);
        addimage=(Button) findViewById(R.id.imagebtn);
        pricetext=(EditText) findViewById(R.id.pricetext);
        imgv=(ImageView) findViewById(R.id.imgview);



        addata.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if (uploadTask!=null && uploadTask.isInProgress())
                {
                    Toast.makeText(DataInputActivity.this,"Upload in Progress Please Wait"
                            ,Toast.LENGTH_LONG).show();
                }
                else {
                    addingdata();
                }


            }
        });

        addimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFilechooser();
            }
        });
    }

    private  String getFileExtension(Uri uri)
    {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private  void  addingdata()
    {
        final String name = getname.getText().toString().trim();
        final String description=getdesc.getText().toString().trim();
        final String price=pricetext.getText().toString().trim();

        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(description) && mImageUri !=null)
        {
            StorageReference filereference = mStorageref.child(System.currentTimeMillis()
            +"."+getFileExtension(mImageUri));

            uploadTask= filereference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            String id=  databaseUser.push().getKey();
                            DataInput datas= new DataInput(id,name,description,price,taskSnapshot.getDownloadUrl().toString());
                            databaseUser.child(id).setValue(datas);

                            Toast.makeText(DataInputActivity.this,"User Added",Toast.LENGTH_LONG).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                }
            });


        }else {
            Toast.makeText(this,"Fill The Form",Toast.LENGTH_LONG).show();
        }
    }



    private void openFilechooser()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data !=null && data.getData() != null)
        {
            mImageUri = data.getData();
            Picasso.with(this).load(mImageUri).into(imgv);

        }
    }

}
