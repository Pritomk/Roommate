package com.techmihirnaik.mergeroommate.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.techmihirnaik.mergeroommate.MainActivity;
import com.techmihirnaik.mergeroommate.R;


public class RegisterActivity extends AppCompatActivity {
    ImageView back;
TextView  logn,regi;
EditText name,email,password;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name=findViewById(R.id.namer);
        email=findViewById(R.id.emailt);
        password=findViewById(R.id.passt);
        mAuth=FirebaseAuth.getInstance();

        back = (ImageView) findViewById(R.id.backr);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        logn=findViewById(R.id.logina);
        logn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });
        regi=findViewById(R.id.register);
        regi.setOnClickListener(new View.OnClickListener() {

            String ema=email.getText().toString();
            @Override
            public void onClick(View view) {
                if(name.getText().toString().isEmpty())
                {
                    name.setError("name cannot be empty");
                }
                else if(email.getText().toString().isEmpty())
                {
                    email.setError("email cannot be empty");
                }
                else if(!email.getText().toString().matches(emailPattern)){
                    email.setError("Enter the correct email");
                }
                else if(password.getText().toString().isEmpty())
                {
                    password.setError("passeord cannot be empty");
                }
                else if(password.getText().toString().length()<8){
                    password.setError("password should be more than 8 characters");
                }
                else{
                    connect();
                }
            }
        });
    }

    private void connect() {
        String emaila=email.getText().toString();
        String pass=password.getText().toString();
        mAuth
                .createUserWithEmailAndPassword(emaila, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),
                                            "Registration successful!",
                                            Toast.LENGTH_LONG)
                                    .show();
                            // if the user created intent to login activity
                            Intent intent
                                    = new Intent(getApplicationContext(),
                                    MainActivity.class);
                            startActivity(intent);
                        }
                        else {

                            // Registration failed
                            Toast.makeText(
                                            getApplicationContext(),
                                            "Registration failed!!"
                                                    + " Please try again later",
                                            Toast.LENGTH_LONG)
                                    .show();


                        }
                    }
                });

    }
}