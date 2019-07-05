package com.luthfi.guestbook.ui.eventdetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.luthfi.guestbook.R
import com.luthfi.guestbook.data.model.Event
import com.luthfi.guestbook.data.model.Guest
import com.luthfi.guestbook.ui.home.GuestAdapter
import kotlinx.android.synthetic.main.activity_event_detail.*
import org.jetbrains.anko.support.v4.onRefresh

class EventDetailActivity : AppCompatActivity(), EventDetailView {

    private lateinit var presenter: EventDetailPresenter
    private lateinit var adapter: GuestAdapter
    private val guest = mutableListOf<Guest?>()
    private lateinit var event: Event

    override fun showLoading() {
        srlEventDetail.isRefreshing = true
    }

    override fun hideLoading() {
        srlEventDetail.isRefreshing = false
    }

    override fun showEventDetail(event: Event?) {
        tvEventName.text = event?.eventName
        tvLocationTime.text = event?.eventLocation + event?.eventDate
        tvEventDesc.text = event?.eventDesc
    }

    override fun showEventGuest(guest: List<Guest?>) {
        this.guest.clear()
        this.guest.addAll(guest)
        adapter.notifyDataSetChanged()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)
        setRecycler()
        event = intent.getParcelableExtra("event")
        presenter = EventDetailPresenter(this, this, event)
        presenter.getEventDetail()
        presenter.getEventGuest()
        onAction()
    }

    private fun onAction() {
        srlEventDetail.onRefresh { presenter.getEventGuest() }
    }

    private fun setRecycler() {
        adapter = GuestAdapter(guest)
        rvGuest.setHasFixedSize(true)
        rvGuest.layoutManager = LinearLayoutManager(this)
        rvGuest.adapter = adapter
    }
}
