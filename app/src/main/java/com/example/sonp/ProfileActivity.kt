package com.example.sonp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_activity)

        val btnAddBook = findViewById<ImageButton>(R.id.btnAddbook)
        val btnHome = findViewById<ImageButton>(R.id.btnHome)
        val btnProfile = findViewById<ImageButton>(R.id.btnProfile)
        val btnLogout = findViewById<Button>(R.id.btnLogout)

        btnAddBook.setOnClickListener {
            val intent = Intent(this, AddbookActivity::class.java)
            startActivity(intent)
        }

        btnHome.setOnClickListener {
            val intent = Intent(this, InterfaceActivity::class.java)
            startActivity(intent)
        }

        btnProfile.setOnClickListener {
            Toast.makeText(this, "Mau kemana bang?", Toast.LENGTH_SHORT).show()
        }

        btnLogout.setOnClickListener {
            logoutUser()
        }
    }

    private fun logoutUser() {
        // Tampilkan pesan logout
        Toast.makeText(this, "Berhasil logout!", Toast.LENGTH_SHORT).show()

        // Arahkan ke MainActivity
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, InterfaceActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }
}
