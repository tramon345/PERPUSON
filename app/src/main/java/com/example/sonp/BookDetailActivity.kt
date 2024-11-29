package com.example.sonp

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class BookDetailActivity : AppCompatActivity() {

    private lateinit var bookKey: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_detail)

        // Ambil data dari Intent
        val title = intent.getStringExtra("title") ?: "No Title"
        val author = intent.getStringExtra("author") ?: "No Author"
        val content = intent.getStringExtra("content") ?: "No Content"
        bookKey = intent.getStringExtra("bookId") ?: ""

        val titleTextView = findViewById<TextView>(R.id.textViewTitle)
        val authorTextView = findViewById<TextView>(R.id.textViewAuthor)
        val contentTextView = findViewById<TextView>(R.id.textViewContent)

        titleTextView.text = title
        authorTextView.text = author
        contentTextView.text = content

        titleTextView.setTextColor(resources.getColor(android.R.color.black))
        authorTextView.setTextColor(resources.getColor(android.R.color.black))
        contentTextView.setTextColor(resources.getColor(android.R.color.black))

        // Tombol Edit
        findViewById<Button>(R.id.buttonEdit).setOnClickListener {
            showEditDialog(title, author, content)
        }

        // Tombol Hapus
        findViewById<Button>(R.id.buttonDelete).setOnClickListener {
            deleteBook()
        }
    }

    // Tampilkan dialog untuk mengedit buku
    private fun showEditDialog(currentTitle: String, currentAuthor: String, currentContent: String) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_edit_book, null)
        val editTitle = dialogView.findViewById<EditText>(R.id.editTextTitle)
        val editAuthor = dialogView.findViewById<EditText>(R.id.editTextAuthor)
        val editContent = dialogView.findViewById<EditText>(R.id.editTextWords)

        editTitle.setText(currentTitle)
        editAuthor.setText(currentAuthor)
        editContent.setText(currentContent)

        AlertDialog.Builder(this)
            .setTitle("Edit Book")
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                val newTitle = editTitle.text.toString().trim()
                val newAuthor = editAuthor.text.toString().trim()
                val newContent = editContent.text.toString().trim()

                if (newTitle.isEmpty() || newAuthor.isEmpty() || newContent.isEmpty()) {
                    Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show()
                } else {
                    updateBook(newTitle, newAuthor, newContent)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    // Fungsi untuk mengganti data lama dengan data baru
    private fun updateBook(newTitle: String, newAuthor: String, newContent: String) {
        val database = FirebaseDatabase.getInstance("https://sonpuser-default-rtdb.asia-southeast1.firebasedatabase.app")
            .getReference("Books")

        // Membuat objek baru untuk mengganti data lama
        val updatedBook = Book(
            bookId = bookKey, // Menggunakan key yang sama
            title = newTitle,
            author = newAuthor,
            content = newContent
        )

        database.child(bookKey).setValue(updatedBook).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Book updated successfully", Toast.LENGTH_SHORT).show()
                finish() // Kembali ke halaman sebelumnya
            } else {
                Toast.makeText(this, "Failed to update book", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Fungsi untuk menghapus buku
    private fun deleteBook() {
        val database = FirebaseDatabase.getInstance("https://sonpuser-default-rtdb.asia-southeast1.firebasedatabase.app")
            .getReference("Books")
        database.child(bookKey).removeValue().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Book deleted successfully", Toast.LENGTH_SHORT).show()
                finish() // Kembali ke halaman sebelumnya
            } else {
                Toast.makeText(this, "Failed to delete book", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
