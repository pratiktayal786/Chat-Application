package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignInAcitivity : AppCompatActivity() {

    private lateinit var  edtName: EditText
    private lateinit var  edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnSignin: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in_acitivity)
        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()
        edtName = findViewById(R.id.edt_name)
        edtEmail = findViewById(R.id.edt_email)
        edtPassword = findViewById(R.id.edt_password)
        btnSignin = findViewById(R.id.btn_signup)

        btnSignin.setOnClickListener{
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()
            val name = edtName.text.toString()
            signIn(name, email, password)
        }
    }

    private fun signIn(name: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    finish()
                    addUserToDatabase(name, email, auth.currentUser?.uid!!)
                    //jump to home activity
                    val intent = Intent(this@SignInAcitivity, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@SignInAcitivity, "Some error occured", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun addUserToDatabase(name: String, email: String, uid: String) {
        dbRef = FirebaseDatabase.getInstance().getReference()

        dbRef.child("user").child(uid).setValue(User(name, email, uid))

    }
}