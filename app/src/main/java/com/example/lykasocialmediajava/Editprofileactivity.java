package com.example.lykasocialmediajava;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.common.net.InternetDomainName;
import com.google.firebase.StartupTime;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Editprofileactivity extends AppCompatActivity {

String imageAccessToken;
ProgressBar editprogressbar;
FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    ImageView imageView;
    EditText username;
    EditText name;
    EditText desc;
    Uri imagepath=null;
    static boolean savable=false;
static int PICK_IMAGE=123;
MaterialButton savebtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofileactivity);

        editprogressbar=findViewById(R.id.editprogressbar);
firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
imageView=findViewById(R.id.editprofileimage);
username=findViewById(R.id.editusername);
name=findViewById(R.id.editname);
desc=findViewById(R.id.editdesc);
savebtn=findViewById(R.id.editsavebtn);



username.setText(Usermodel.getUsername());
name.setText(Usermodel.getName());
desc.setText(Usermodel.getDesc());
if(Usermodel.getImageurl()!=null)
{

    Picasso.get().load(Usermodel.getImageurl()).into(imageView);
}








imageView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);

        startActivityForResult(intent,PICK_IMAGE);
    }
});




        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(username.getText().toString().isEmpty())
                {
                    Toast.makeText(Editprofileactivity.this, "Enter username", Toast.LENGTH_SHORT).show();
                }
                else{
                    editprogressbar.setVisibility(View.VISIBLE);
                    Query usernameres = firebaseFirestore.collection("users").whereEqualTo("username", username.getText().toString());

                    usernameres.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            if(task.isSuccessful()) {


                                Map<String, Object> doc=new HashMap<String,Object>();
try {
    QuerySnapshot documentSnapshot = task.getResult();
    List<DocumentSnapshot> details = documentSnapshot.getDocuments();
    DocumentSnapshot documentSnapshot1 = details.get(0);
   doc = documentSnapshot1.getData();
    Log.e("*", doc.get("userID").toString());

}
catch( Exception e)
{
    Log.e("*", e.toString());
}



                                if ((task.getResult().size() ==1 ) && !firebaseAuth.getUid().toString().equals(doc.get("userID").toString())) {
                                      Log.e("*","uoppop");
                                    editprogressbar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(Editprofileactivity.this, "username is taken", Toast.LENGTH_SHORT).show();

                                }
                                else{


                                    sendImagetoStorage();

                                    Usermodel.setUsername(username.getText().toString());
                                    Usermodel.setName(name.getText().toString());
                                    Usermodel.setDesc(desc.getText().toString());

                                    if(imagepath!=null) {

                                        Usermodel.setImageurl(imageAccessToken);

                                    }


                                    editprogressbar.setVisibility(View.INVISIBLE);
                                        startActivity(new Intent(Editprofileactivity.this, MainActivity.class));
                                        finish();

                                }

                            }
                        }
                    });






                }


            }
        });


    }


    private  void sendImagetoStorage()
    {
        if(imagepath!=null)
        {
            Log.e("*","image path not null");
            FirebaseStorage firebaseStorage;
            firebaseStorage=FirebaseStorage.getInstance();
            StorageReference storageReference=firebaseStorage.getReference();
            StorageReference iamgeRef = storageReference.child(firebaseAuth.getUid()).child("profilepicture").child("profilepic");



            // image compression

            Bitmap bitmap = null;

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
                    iamgeRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            imageAccessToken = uri.toString();
                            sendDatatocloudFirestore();
                        }
                    });
                }
            });
        }
        else {
            sendDatatocloudFirestore();
        }

    }


    private  void  sendDatatocloudFirestore()

    {

        DocumentReference documentReference=firebaseFirestore.collection("users").document(firebaseAuth.getUid());

        Map<String,Object> userdata=new HashMap<>();

        userdata.put("username",username.getText().toString());

        userdata.put("name",name.getText().toString());
        userdata.put("description",desc.getText().toString());

        documentReference.update("username",username.getText().toString());
        documentReference.update("name",name.getText().toString());
        documentReference.update("description",desc.getText().toString());


if(imagepath!=null)
{
    documentReference.update("imageurl",imageAccessToken);
}






    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        Log.e("*","yooooooooooooooooo");
        // if user selected a image fromm gallary
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK)
        {

            imagepath=data.getData();
            imageView.setImageURI(imagepath);
        }

        Log.e("*","image path"+imagepath);
        super.onActivityResult(requestCode, resultCode, data);


    }
}