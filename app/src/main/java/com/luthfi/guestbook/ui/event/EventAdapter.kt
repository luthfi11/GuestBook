package com.luthfi.guestbook.ui.event

import android.database.DatabaseUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.luthfi.guestbook.R
import com.luthfi.guestbook.data.db.database
import com.luthfi.guestbook.data.model.Event
import com.luthfi.guestbook.data.model.Guest
import com.luthfi.guestbook.ui.eventdetail.EventDetailActivity
import com.luthfi.guestbook.util.DateUtil.dateFormat
import kotlinx.android.synthetic.main.item_event.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity

class EventAdapter(private val event: MutableList<Event?>) : RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false))
    }

    override fun getItemCount(): Int {
        return event.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(event[position])
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindItem(event: Event?) {
            itemView.tvEventName.text = event?.eventName
            itemView.tvLocation.text = event?.eventLocation
            itemView.tvEventDate.text = dateFormat(event?.eventDate, "EEEE, dd MMMM yyyy")
            itemView.tvGuestCount.text = "${getCount(event?.id)}"
            itemView.rlv.onClick { itemView.context.startActivity<EventDetailActivity>("eventId" to event?.id) }
        }

        private fun getCount(eid: Int?) = DatabaseUtils.queryNumEntries(
            itemView.context.database.readableDatabase,
            Guest.TABLE_GUEST,
            "${Guest.EVENT_ID} = $eid"
        )
    }

}