package com.example.lykasocialmediajava;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Newpostactivity extends AppCompatActivity {
ImageView imageView;
VideoView videoView;

EditText newposttextedit;
RelativeLayout imagevideoreltvel;
Uri imagepath=null;
boolean isVideo=false;
FirebaseAuth firebaseAuth;
FirebaseFirestore firebaseFirestore;
MaterialButton newpostsendbtn;
String imageAccessToken;
int PICK_IMAGE=123;
ProgressBar newpostprogress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newpostactivity);

        newpostprogress=findViewById(R.id.newpostprogress);

        imageView=findViewById(R.id.newpostimage);
        videoView=findViewById(R.id.newpostvideo);
        imagevideoreltvel=findViewById(R.id.imagevideoreltvel);
        newpostsendbtn=findViewById(R.id.newpostsendbtn);
        newposttextedit=findViewById(R.id.newposttextedit);
firebaseAuth=FirebaseAuth.getInstance();
firebaseFirestore =FirebaseFirestore.getInstance();


        // selecct file
        imagevideoreltvel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/* video/*");
                startActivityForResult(intent,PICK_IMAGE);
            }
        });


        // post btn

        newpostsendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imagepath==null && newposttextedit.getText().toString().isEmpty()){


                    Toast.makeText(Newpostactivity.this, "post cant be wmpty", Toast.LENGTH_SHORT).show();
                }
else {
    newpostprogress.setVisibility(View.VISIBLE);
                    sendImagetoStorage();

                    newpostprogress.setVisibility(View.INVISIBLE);
                    startActivity(new Intent(Newpostactivity.this,MainActivity.class));
                    finish();

                }

            }
        });


    }


    private  void sendImagetoStorage()
    {
        if(imagepath!=null) {


            Log.e("*", "image path not null");
            FirebaseStorage firebaseStorage;
            firebaseStorage = FirebaseStorage.getInstance();
            StorageReference storageReference = firebaseStorage.getReference();
            StorageReference iamgeRef = storageReference.child("posts").child(Math.random() + "" + Math.random() + " " + Math.random());


            // image compression

            Bitmap bitmap = null;

            if (isVideo == false) {

                try {

                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagepath);


                } catch (IOException e) {
                    Log.e("*", " exp " + e.toString());

                }


                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 25, byteArrayOutputStream);


                byte[] data = byteArrayOutputStream.toByteArray();

                // save in storage
                UploadTask uploadTask = iamgeRef.putBytes(data);

                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Log.e("*", "success");

                        iamgeRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                imageAccessToken = uri.toString();
                                Log.e("*", imageAccessToken);
                                sendDatatocloudFirestore();
                            }
                        });
                    }
                });

            }
            else{


                StorageReference videoref = storageReference.child("posts").child(Math.random() + "" + Math.random() + " " + Math.random()+".mp4");

                // save in storage
                UploadTask uploadTask = videoref.putFile(imagepath);

              uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                  @Override
                  public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                      videoref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                          @Override
                          public void onSuccess(Uri uri) {
                              imageAccessToken = uri.toString();
                              Log.e("*", imageAccessToken);
                              sendDatatocloudFirestore();
                          }
                      });
                  }
              });


            }
            } else {
                sendDatatocloudFirestore();
            }




    }

    void sendDatatocloudFirestore()
    {

        CollectionReference collectionReference = firebaseFirestore.collection("posts");
        Map<String,String > details=new HashMap<>();
        details.put("userID",firebaseAuth.getUid());
//        details.put("username",Usermodel.getUsername());
//        details.put("userprofileimage",Usermodel.getImageurl());
        details.put("postimage","");
        details.put("posttext","");
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");


        details.put("timetsamp",    simpleDateFormat.format(calendar.getTime()).toString());

        if(imagepath!=null)
      {
details.put("postimage",imageAccessToken);
      }
      if(!newposttextedit.getText().toString().isEmpty())
      {
          details.put("posttext",newposttextedit.getText().toString());

      }
String post_id= (firebaseAuth.getUid()+Math.random()+""+Math.random());

        details.put("post_id",post_id);



        collectionReference.document(post_id).set(details);

//  do like
//        CollectionReference likescollectionReference = firebaseFirestore.collection("Likes");
//
//        Map<String,String > likesdetails=new HashMap<>();
//        likesdetails.put("userID",firebaseAuth.getUid());
//        likesdetails.put("postID",post_id);
//
//        likesdetails.put("username",Usermodel.getUsername());
//
//        likesdetails.put("userprofileimage",Usermodel.getImageurl());
//
//
//        likescollectionReference.document(post_id).set(likesdetails);


        //  do commnets

//
//        CollectionReference commnetcollectionReference = firebaseFirestore.collection("Comments");
//
//        Map<String,String > comdetails=new HashMap<>();
//        comdetails.put("userID",firebaseAuth.getUid());
//        comdetails.put("postID",post_id);
//
//        comdetails.put("username",Usermodel.getUsername());
//comdetails.put("commenttext","lolol");
//        comdetails.put("userprofileimage",Usermodel.getImageurl());
//
//
//        commnetcollectionReference.document(post_id).set(comdetails);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

if(requestCode==PICK_IMAGE && resultCode==RESULT_OK)
{

imagepath=data.getData();

    if(imagepath.toString().contains("image"))
    {
isVideo=false;
Log.e("*","image");
videoView.setVisibility(View.GONE);
imageView.setVisibility(View.VISIBLE);
imageView.setPadding(1,1,1,1);
imageView.setImageURI(imagepath);


    }
    else if(imagepath.toString().contains("video"))
    {
        isVideo=true;
        imageView.setVisibility(View.GONE);
        Log.e("*","video");
        videoView.setVisibility(View.VISIBLE);

        videoView.setPadding(1,1,1,1);
        videoView.setVideoURI(imagepath);


        // starts the video
        videoView.start();

    }
}

        super.onActivityResult(requestCode, resultCode, data);


    }


}