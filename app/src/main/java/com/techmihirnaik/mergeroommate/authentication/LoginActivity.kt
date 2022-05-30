package com.techmihirnaik.mergeroommate.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.techmihirnaik.mergeroommate.MainActivity
import com.techmihirnaik.mergeroommate.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var evEmail: EditText
    private lateinit var evPassword: EditText
    private lateinit var btnLogin: TextView
    private lateinit var btnReg: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = Firebase.auth

        evEmail = binding.etEmail
        evPassword = binding.etPassword
        btnLogin = binding.login
        btnReg = binding.signup

        btnReg.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

        btnLogin.setOnClickListener {
            loginFunc()
        }
    }

    private fun loginFunc() {

        val email = evEmail.text.toString()
        val password = evPassword.text.toString()

        if (email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            if (password.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                    startActivity(Intent(this, MainActivity::class.java))
                }.addOnFailureListener {
                    evPassword.error = "Wrong Password"
                }
            } else {
                evPassword.error = "Enter password"
            }
        } else {
            evEmail.error = "Enter email address"
        }
    }

    override fun onStart() {
        super.onStart()

        if (firebaseAuth.currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}