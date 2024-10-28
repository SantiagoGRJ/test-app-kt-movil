package com.example.testapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_forgot_password)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var editTextEmail = findViewById<EditText>(R.id.editTextEmail)
        var button_forgot_password = findViewById<Button>(R.id.button_forgot_password)

        auth = Firebase.auth

        val currentUser = auth.currentUser

        button_forgot_password.setOnClickListener{
            var email : String = editTextEmail.text.toString()
            if(isEmpty(email)) return@setOnClickListener  Toast.makeText(this, "Empty field are not allowed", Toast.LENGTH_SHORT).show()
            sendPasswordResetEmail(email)
            redirectToLogin()
        }

    }

    private fun sendPasswordResetEmail(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Correo de restablecimiento enviado.", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            }

    }

    private fun isEmpty(email: String): Boolean {
        return email.isEmpty()
    }

    private fun redirectToLogin(){
        val intent = Intent(this, LogInActivity::class.java)
        startActivity(intent)
        finish()
    }

}