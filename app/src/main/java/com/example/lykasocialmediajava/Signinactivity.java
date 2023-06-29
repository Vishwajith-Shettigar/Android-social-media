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
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Signinactivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    FirebaseFirestore firebaseFirestore;
    TextInputEditText usernameedittext;
    TextInputEditText emailedittext;
    TextInputEditText passwordedittext;

    TextView signuoherebtn;

    MaterialButton signinbtn;
    String email,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signinactivity);

        firebaseAuth=FirebaseAuth.getInstance();


        signuoherebtn=findViewById(R.id.signupherebtn);

        emailedittext=findViewById(R.id.email);
        passwordedittext=findViewById(R.id.password);
        signinbtn=findViewById(R.id.signinbtn);



        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {

                email=emailedittext.getText().toString().trim();
                password=passwordedittext.getText().toString().trim();


                if(!email.contains("@") )
                {

                    Toast.makeText(Signinactivity.this, "Enter valid email", Toast.LENGTH_SHORT).show();
                    return;
                }

                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Signinactivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("*", "signInWithEmail:success");
                                    FirebaseUser user = firebaseAuth.getCurrentUser();


                                    Intent intent=new Intent(Signinactivity.this,MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("*", "signInWithEmail:failure", task.getException());
                                    Toast.makeText(Signinactivity.this, "Account doesnot exists",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

            }
        });

    }
}