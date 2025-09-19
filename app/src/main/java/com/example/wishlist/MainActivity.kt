package com.example.wishlist

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: WishlistAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var addButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
            recyclerView = findViewById(R.id.recyclerView)
            addButton = findViewById(R.id.addButton)

            adapter = WishlistAdapter(mutableListOf())
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this)

            addButton.setOnClickListener {
                showAddItemDialog()
            }
        }

         fun showAddItemDialog() {
            val dialogView = layoutInflater.inflate(R.layout.dialog_add_item, null)
            val nameInput = dialogView.findViewById<EditText>(R.id.nameInput)
            val priceInput = dialogView.findViewById<EditText>(R.id.priceInput)
            val urlInput = dialogView.findViewById<EditText>(R.id.urlInput)

            AlertDialog.Builder(this)
                .setTitle("Add Wishlist Item")
                .setView(dialogView)
                .setPositiveButton("Add") { _, _ ->
                    val name = nameInput.text.toString()
                    val price = priceInput.text.toString()
                    val url = urlInput.text.toString()
                    if (name.isNotEmpty() && price.isNotEmpty() && url.isNotEmpty()) {
                        adapter.addItem(WishlistItem(name, price, url))
                    }
                }
                .setNegativeButton("Cancel", null)
                .create()
                .show()
        }
    }
}