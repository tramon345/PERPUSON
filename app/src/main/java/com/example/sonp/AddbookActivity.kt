package com.example.sonp

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddbookActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addbook_activity)

        // Inisialisasi Firebase Database
        database = FirebaseDatabase.getInstance("https://sonpuser-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("Books")

        // Inisialisasi view
        val titleEditText = findViewById<EditText>(R.id.editTextTitle)
        val authorEditText = findViewById<EditText>(R.id.editTextAuthor)
        val wordsEditText = findViewById<EditText>(R.id.editTextWords)
        val publishButton = findViewById<Button>(R.id.buttonPublish)

        // Navigasi antar halaman
        val btnAddBook = findViewById<ImageButton>(R.id.btnAddbook)
        val btnHome = findViewById<ImageButton>(R.id.btnHome)
        val btnProfile = findViewById<ImageButton>(R.id.btnProfile)

        // Tombol Publikasi Buku
        publishButton.setOnClickListener {
            val title = titleEditText.text.toString()
            val author = authorEditText.text.toString()
            val content = wordsEditText.text.toString()

            // Validasi input
            if (TextUtils.isEmpty(title)) {
                titleEditText.error = "Judul tidak boleh kosong"
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(author)) {
                authorEditText.error = "Pengarang tidak boleh kosong"
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(content)) {
                wordsEditText.error = "Cerita tidak boleh kosong"
                return@setOnClickListener
            }

            val bookId = database.push().key ?: return@setOnClickListener
            val book = Book(bookId, title, author, content)

            database.child(bookId).setValue(book).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Cerita berhasil dipublikasikan", Toast.LENGTH_SHORT).show()
                    titleEditText.text.clear()
                    authorEditText.text.clear()
                    wordsEditText.text.clear()
                } else {
                    Toast.makeText(this, "Gagal mempublikasikan cerita", Toast.LENGTH_SHORT).show()
                }
            }
        }

        btnAddBook.setOnClickListener {
            Toast.makeText(this, "Mau kemana bang?", Toast.LENGTH_SHORT).show()
        }

        btnHome.setOnClickListener {
            val intent = Intent(this, InterfaceActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }

        btnProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, InterfaceActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }
}
