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

class RegisterAccountActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register_account)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var editTextEmail : EditText= findViewById(R.id.editTextEmail)
        var editTextPassword : EditText = findViewById(R.id.editTextPassword)
        var button_register : Button = findViewById(R.id.button_register)

        // Initialize Firebase Auth
        auth = Firebase.auth


        button_register.setOnClickListener{
            var email : String = editTextEmail.text.toString()
            var password : String = editTextPassword.text.toString()

            if(isEmpty(email,password)) return@setOnClickListener  Toast.makeText(this, "Empty fields are not allowed", Toast.LENGTH_SHORT).show()
            if(verifyFields(email,password)) return@setOnClickListener Toast.makeText(this, "Invalid email or password length is less than 6", Toast.LENGTH_SHORT).show()

            registerAccount(email,password)


        }

    }

    private fun registerAccount(email: String, password: String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(
                        baseContext,
                        "Account created.",
                        Toast.LENGTH_SHORT,
                    ).show()
                    redirectToHome()
                    val user = auth.currentUser

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        baseContext,
                        "Account creation failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }

    private fun isEmpty(email: String,password: String): Boolean {
        return email.isEmpty() || password.isEmpty()
    }
    private fun verifyFields(email: String, password: String): Boolean {
        return password.length < 6 || !email.contains("@")
    }

    private fun redirectToHome(){
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }


}