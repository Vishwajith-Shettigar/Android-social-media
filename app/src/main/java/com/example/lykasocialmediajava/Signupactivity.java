package com.example.lykasocialmediajava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Signupactivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    FirebaseFirestore firebaseFirestore;
    TextInputEditText usernameedittext;
    TextInputEditText emailedittext;
    TextInputEditText passwordedittext;

    TextView signinherebtn;

MaterialButton signupbtn;
String username,email,password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupactivity);


        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        DocumentReference documentReference = firebaseFirestore.collection("users").document("user");

        signinherebtn=findViewById(R.id.signinherebtn);
        usernameedittext=findViewById(R.id.username);
        emailedittext=findViewById(R.id.email);
        passwordedittext=findViewById(R.id.password);
signupbtn=findViewById(R.id.signupbtn);




signupbtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        username=usernameedittext.getText().toString().trim();

        email=emailedittext.getText().toString().trim();
        password=passwordedittext.getText().toString().trim();

        if(username.isEmpty() || password.isEmpty() || email.isEmpty())
        {
            Toast.makeText(Signupactivity.this, "Enter details", Toast.LENGTH_SHORT).show();
            return;

        }
        if(email.contains(" "))
        {
            Toast.makeText(Signupactivity.this, "Enter valid email", Toast.LENGTH_SHORT).show();
            return;
        }

        if(username.contains(" ") || username.contains("+") || username.contains("-") || username.contains("/")|| username.contains("*")   )
        {

            Toast.makeText(Signupactivity.this, "Usrename only could have alphabets,numbers and underscore", Toast.LENGTH_SHORT).show();
        }


        else{



            Query usernameres = firebaseFirestore.collection("users").whereEqualTo("username", username);
Log.e("*",username);

usernameres.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
    @Override
    public void onComplete(@NonNull Task<QuerySnapshot> task) {
        if(task.isSuccessful()) {


            if(task.getResult().size()!=0)
            {
Log.e("*","already exists");
                Toast.makeText(Signupactivity.this, "username is taken", Toast.LENGTH_SHORT).show();
            }
            else{
                Log.e("*","dosmot  exists");
            email=emailedittext.getText().toString().trim();
            password=passwordedittext.getText().toString().trim();


if(!email.contains("@") )
{

    Toast.makeText(Signupactivity.this, "Enter valid email", Toast.LENGTH_SHORT).show();
    return;
}
if(password.length()<=5)
{
    Toast.makeText(Signupactivity.this, "password must be 6 character or more", Toast.LENGTH_SHORT).show();
    return;

}


//
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(Signupactivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("*", "createUserWithEmail:success");
                                        FirebaseUser user = firebaseAuth.getCurrentUser();

// saving detials in firestore
                                       CollectionReference collectionReference = firebaseFirestore.collection("users");
                                        Map<String,String > details=new HashMap<>();
                                        details.put("userID",firebaseAuth.getUid());
                                        details.put("username",username);
                                        details.put("email",email);
                                        details.put("password",password);
                                            collectionReference.document("user").set(details);



                                        Intent intent=new Intent(Signupactivity.this,MainActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);


                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("*", "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(Signupactivity.this, " Account exist",
                                                Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });

//



            }
        }

    }
});




                        // end

        }


    }
});

        signinherebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(Signupactivity.this,Signinactivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

if(FirebaseAuth.getInstance().getCurrentUser()!=null)
{
    Intent intent=new Intent(Signupactivity.this,MainActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    startActivity(intent);
}
    }
}