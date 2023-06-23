package com.example.epdfreader;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.Objects;

public class browserActivity extends AppCompatActivity {
  ImageView browse , cross , upload , pdf;
  EditText title;
  Uri filepath;
   StorageReference storageReference;
   DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        browse=findViewById(R.id.broeseimg);
        cross = findViewById(R.id.cancealimg);
        upload  =findViewById(R.id.uploadimg);
        pdf = findViewById(R.id.pdfimg);
        title = findViewById(R.id.titleedit);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN , WindowManager.LayoutParams.FLAG_FULLSCREEN);
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("mydocument");

        // firstly both the cross and file will be not appear
        cross.setVisibility(View.INVISIBLE);
        pdf.setVisibility(View.INVISIBLE);
        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cross.setVisibility(View.INVISIBLE);
                pdf.setVisibility(View.INVISIBLE);
                browse.setVisibility(View.VISIBLE);
            }
          });
          browse.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Dexter.withContext(getApplicationContext())
                          .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                          .withListener(new PermissionListener() {
                              @Override
                              public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                  Intent intent =new Intent();
                                  intent.setType("application/pdf");
                                  intent.setAction(Intent.ACTION_GET_CONTENT);
                                  startActivityForResult(Intent.createChooser(intent , "selects pdf file") , 101);

                              }

                              @Override
                              public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                              }

                              @Override
                              public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                  permissionToken.continuePermissionRequest();
                              }
                          }).check();
              }
          });
          upload.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  processupload(filepath);
              }
          });
        }

    private void processupload(Uri filepath) {

        ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("File uploaded......!!");
        pd.show();
        // first we have to go to storage to recieved the file uri
        // system time is given for the unique value generated every uri
      StorageReference reference = storageReference.child("upload/"+System.currentTimeMillis() + ".pdf");
         reference.putFile(filepath)
                 .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                     @Override
                     public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                         // now we have to find the link of uploaded file
                         reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                             @Override
                             public void onSuccess(Uri uri) {
                              myModel obj = new myModel(title.getText().toString() , uri.toString());

                                  // putting the link in the realtime database
                                 databaseReference.child(databaseReference.push().getKey()).setValue(obj);

                                 pd.dismiss();
                                 Toast.makeText(browserActivity.this, "File uploaded", Toast.LENGTH_LONG).show();
                                 cross.setVisibility(View.INVISIBLE);
                                 pdf.setVisibility(View.INVISIBLE);
                                 browse.setVisibility(View.VISIBLE);
                                 title.setText("");
                             }
                         });
                     }
                 }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                     @Override
                     public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                          float percenatge = (100*snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                          pd.setMessage("Uploaded :" +(int)percenatge + "%");
                     }
                 });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode== 101 && resultCode == RESULT_OK){
            filepath = data.getData();
            cross.setVisibility(View.VISIBLE);
            pdf.setVisibility(View.VISIBLE);
            browse.setVisibility(View.INVISIBLE);
        }

    }
}