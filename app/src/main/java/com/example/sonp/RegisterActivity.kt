package com.example.sonp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sonp.databinding.RegisterActivityBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: RegisterActivityBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RegisterActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance("https://sonpuser-default-rtdb.asia-southeast1.firebasedatabase.app/").
        getReference("users")

        binding.btrg.setOnClickListener {
            val email = binding.te.editText?.text.toString()
            val password = binding.tp.editText?.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                registerUser(email, password)
            } else {
                Toast.makeText(this, "Email atau Password tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btback.setOnClickListener {
            finish()
        }
    }

    private fun registerUser(email: String, password: String) {
        val userId = database.push().key

        if (userId != null) {
            val user = User(userId, email, password)

            database.child(userId).setValue(user)
                .addOnSuccessListener {
                    Toast.makeText(this, "Registrasi berhasil!", Toast.LENGTH_SHORT).show()
                    navigateToLogin()
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(this, "Gagal menyimpan data pengguna: ${exception.message}", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "Gagal membuat ID pengguna", Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }
}

// Data class for User
data class User(
    val userId: String = "",
    val email: String = "",
    val password: String = ""
) {
    // Firebase requires a no-argument constructor
    constructor() : this("", "", "")  // No-argument constructor
}

