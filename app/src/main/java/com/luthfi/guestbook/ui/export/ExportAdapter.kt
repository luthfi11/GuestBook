package com.luthfi.guestbook.ui.export

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.luthfi.guestbook.R
import com.luthfi.guestbook.data.model.Guest
import com.wysiwyg.temanolga.utilities.gone
import com.wysiwyg.temanolga.utilities.visible
import kotlinx.android.synthetic.main.item_export.view.*

class ExportAdapter(private val guest: MutableList<Guest?>) : RecyclerView.Adapter<ExportAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_export,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return guest.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(position+1, guest[position])
        holder.showTitle(position)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindItem(i: Int, guest: Guest?) {
            itemView.tvNumber.text = "$i"
            itemView.tvGuestName.text = guest?.name
            itemView.tvGuestAddress.text = guest?.address
            itemView.tvGuestEmail.text = guest?.email
            itemView.tvGuestPhone.text = guest?.phone
            itemView.tvGuestNote.text = guest?.guestNote
        }

        fun showTitle(i: Int) {
            if (i == 0) itemView.lytTitle.visible()
            else itemView.lytTitle.gone()
        }
    }
}