package com.example.wishlist

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class WishlistAdapter(
    private val items: MutableList<WishlistItem>
) : RecyclerView.Adapter<WishlistAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameText: TextView = itemView.findViewById(R.id.itemName)
        val priceText: TextView = itemView.findViewById(R.id.itemPrice)
        val urlText: TextView = itemView.findViewById(R.id.itemUrl)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_wishlist, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: WishlistItem = items[position]

        holder.nameText.text = item.name
        holder.priceText.text = item.price
        holder.urlText.text = item.url

        // Click → open browser
        holder.itemView.setOnClickListener {
            try {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(item.url))
                ContextCompat.startActivity(it.context, browserIntent, null)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(it.context, "Invalid URL for ${item.name}", Toast.LENGTH_LONG).show()
            }
        }

        // Long press → delete
        holder.itemView.setOnLongClickListener {
            items.removeAt(position)
            notifyItemRemoved(position)
            true
        }
    }

    fun addItem(item: WishlistItem) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }
}