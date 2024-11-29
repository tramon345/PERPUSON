package com.example.sonp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class InterfaceActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var bookList: MutableList<Book>
    private lateinit var database: DatabaseReference
    private lateinit var adapter: BookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.inteface_activity)

        recyclerView = findViewById(R.id.recyclerViewBooks)
        recyclerView.layoutManager = LinearLayoutManager(this)
        bookList = mutableListOf()
        database = FirebaseDatabase.getInstance("https://sonpuser-default-rtdb.asia-southeast1.firebasedatabase.app")
            .getReference("Books")

        adapter = BookAdapter(bookList) { book ->
            val intent = Intent(this@InterfaceActivity, BookDetailActivity::class.java)
            intent.putExtra("bookId", book.bookId)
            intent.putExtra("title", book.title)
            intent.putExtra("author", book.author)
            intent.putExtra("content", book.content)
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        loadBooks()

        val btnHome = findViewById<ImageButton>(R.id.btnAddbook)
        val btnSearch = findViewById<ImageButton>(R.id.btnHome)
        val btnProfile = findViewById<ImageButton>(R.id.btnProfile)

        btnHome.setOnClickListener {
            val intent = Intent(this, AddbookActivity::class.java)
            startActivity(intent)
        }

        btnSearch.setOnClickListener {
            Toast.makeText(this, "Mau kemana bang?", Toast.LENGTH_SHORT).show()
        }

        btnProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadBooks() {
        database.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                bookList.clear()
                for (bookSnapshot in snapshot.children) {
                    val book = bookSnapshot.getValue(Book::class.java)
                    if (book != null) {
                        bookList.add(book)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@InterfaceActivity, "Failed to load books", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
