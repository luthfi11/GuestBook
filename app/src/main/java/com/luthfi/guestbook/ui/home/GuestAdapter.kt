package com.luthfi.guestbook.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.luthfi.guestbook.R
import com.luthfi.guestbook.data.model.Guest
import kotlinx.android.synthetic.main.item_guest.view.*
import org.jetbrains.anko.imageResource

class GuestAdapter(private val guest: MutableList<Guest?>): RecyclerView.Adapter<GuestAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_guest, parent, false))
    }

    override fun getItemCount(): Int {
        return guest.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(guest[position])
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        fun bindItem(guest: Guest?) {
            itemView.tvName.text = guest?.name
            itemView.tvEmail.text = guest?.email
            itemView.imgAva.imageResource = R.drawable.ava
        }
    }
}