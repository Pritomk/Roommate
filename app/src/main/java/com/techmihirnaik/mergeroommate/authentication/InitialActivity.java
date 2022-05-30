package com.techmihirnaik.mergeroommate.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.techmihirnaik.mergeroommate.MainActivity;
import com.techmihirnaik.mergeroommate.R;


public class InitialActivity extends AppCompatActivity {

    TextView log ,Sign;
    FirebaseAuth auth;
    private String TAG = "com.techmihirnaik.mergeroommate.authentication.InitialActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);
        log=findViewById(R.id.login);
        Sign= findViewById(R.id.register);

        log.setOnClickListener(view ->
                startActivity( new Intent(getApplicationContext(), LoginActivity.class)));
        Sign.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class)));

        auth = FirebaseAuth.getInstance();

    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d(TAG, auth.getUid()+"");
        if (auth.getUid() != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }
}