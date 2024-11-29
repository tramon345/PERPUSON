package com.example.sonp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        database = FirebaseDatabase.getInstance("https://sonpuser-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("users")

        val emailField = findViewById<TextInputLayout>(R.id.te).editText
        val passwordField = findViewById<TextInputLayout>(R.id.tp).editText
        val loginButton = findViewById<Button>(R.id.btnLogin)
        val registerButton = findViewById<Button>(R.id.btnSignin)
        val forgotPassword = findViewById<TextView>(R.id.tvForgotPassword)

        // Login Button Logic
        loginButton.setOnClickListener {
            val email = emailField?.text.toString()
            val password = passwordField?.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginUser(email, password)
            } else {
                Toast.makeText(this, "Email dan password tidak boleh kosong.", Toast.LENGTH_SHORT).show()
            }
        }

        // Register Button Logic (navigate to RegisterActivity)
        registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // Forgot Password Logic
        forgotPassword.setOnClickListener {
            Toast.makeText(this, "Maaf fitur ini belum tersedia.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loginUser(email: String, password: String) {
        database.orderByChild("email").equalTo(email)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (userSnapshot in snapshot.children) {
                            val user = userSnapshot.getValue(User::class.java)
                            if (user != null && user.password == password) {
                                // Successful login
                                Toast.makeText(this@MainActivity, "Login berhasil!", Toast.LENGTH_SHORT).show()

                                // Navigate to InterfaceActivity
                                val intent = Intent(this@MainActivity, InterfaceActivity::class.java)
                                startActivity(intent)
                                finish() // Finish MainActivity to prevent going back to it
                                return
                            }
                        }
                        Toast.makeText(this@MainActivity, "Password salah!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@MainActivity, "Pengguna tidak ditemukan.", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@MainActivity, "Gagal login: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }
}
