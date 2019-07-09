package com.luthfi.guestbook.ui.eventdetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.luthfi.guestbook.R
import com.luthfi.guestbook.data.model.Guest
import com.luthfi.guestbook.ui.guestdetail.GuestDetailActivity
import kotlinx.android.synthetic.main.item_guest.view.*
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity

class GuestAdapter(private val guest: MutableList<Guest?>) : RecyclerView.Adapter<GuestAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_guest,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return guest.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(guest[position])
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindItem(guest: Guest?) {
            itemView.tvName.text = guest?.name
            itemView.tvEmail.text = guest?.email
            itemView.imgAva.imageResource = R.drawable.ava

            itemView.rlv.onClick { itemView.context.startActivity<GuestDetailActivity>("guestId" to guest?.id) }
        }

    }
}