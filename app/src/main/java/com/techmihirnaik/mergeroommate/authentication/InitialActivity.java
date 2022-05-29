package com.techmihirnaik.mergeroommate.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.techmihirnaik.mergeroommate.MainActivity;
import com.techmihirnaik.mergeroommate.R;


public class InitialActivity extends AppCompatActivity {

    TextView log ,Sign;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);
        log=findViewById(R.id.login);
        Sign= findViewById(R.id.register);

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
        Sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });

        auth = FirebaseAuth.getInstance();

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (auth.getUid() != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }
}