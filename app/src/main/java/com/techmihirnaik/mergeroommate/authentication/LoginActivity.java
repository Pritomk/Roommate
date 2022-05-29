package com.techmihirnaik.mergeroommate.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.techmihirnaik.mergeroommate.MainActivity;
import com.techmihirnaik.mergeroommate.R;

public class LoginActivity extends AppCompatActivity {
ImageView back;
TextView log,sign;
EditText emails,pass;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emails=findViewById(R.id.username);
        pass=findViewById(R.id.passwordedit);
        back=findViewById(R.id.back);
        log=findViewById(R.id.login);
        mAuth=FirebaseAuth.getInstance();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(emails.getText().toString().isEmpty()){
                    emails.setError("email cannot be empty");
                }
                else if(!emails.getText().toString().matches(emailPattern)){
                    emails.setError("Enter the correct email");
                }
                else if(pass.getText().toString().isEmpty()){
                    pass.setError("password cannot be empty");
                }
                else if(pass.getText().toString().length()<8){
                    pass.setError("password must contain more than 8 characters");

                }
                else{
                    perfomrlogin();

                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
            }
        });
sign=findViewById(R.id.signin);
sign.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(new Intent(getApplicationContext(), RegisterActivity.class));

    }
});
    }

    private void perfomrlogin() {
        String email=emails.getText().toString();
        String passa=pass.getText().toString();
        mAuth.signInWithEmailAndPassword(email,passa).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
    }
}