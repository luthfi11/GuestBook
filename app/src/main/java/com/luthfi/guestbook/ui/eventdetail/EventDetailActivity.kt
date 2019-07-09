package com.luthfi.guestbook.ui.eventdetail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.luthfi.guestbook.R
import com.luthfi.guestbook.data.model.Event
import com.luthfi.guestbook.data.model.Guest
import com.luthfi.guestbook.ui.addguest.AddGuestActivity
import com.luthfi.guestbook.ui.editevent.EditEventActivity
import com.luthfi.guestbook.util.DateUtil
import com.wysiwyg.temanolga.utilities.gone
import com.wysiwyg.temanolga.utilities.visible
import kotlinx.android.synthetic.main.activity_event_detail.*
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.onRefresh

class EventDetailActivity : AppCompatActivity(), EventDetailView, SearchView.OnQueryTextListener {

    private lateinit var presenter: EventDetailPresenter
    private lateinit var adapter: GuestAdapter
    private val guest = mutableListOf<Guest?>()
    private var event: Event? = null

    override fun showLoading() {
        srlEventDetail.isRefreshing = true
        svGuest.setQuery("", false)
        svGuest.clearFocus()
    }

    override fun hideLoading() {
        srlEventDetail.isRefreshing = false
    }

    @SuppressLint("SetTextI18n")
    override fun showEventDetail(event: Event?) {
        this.event = event
        tvEventName.text = event?.eventName
        tvLocationTime.text = "${event?.eventLocation}, ${DateUtil.dateFormat(event?.eventDate, "dd MMMM yyyy")}"
        tvEventDesc.text = event?.eventDesc
    }

    override fun showEventGuest(guest: List<Guest?>) {
        this.guest.clear()
        this.guest.addAll(guest)
        adapter.notifyDataSetChanged()
        tvGuestCount.text = String.format(getString(R.string.showing_count), guest.size)
        onEmpty(guest)
    }

    override fun showDeleteAlert() {
        alert {
            messageResource = R.string.delete_event_msg
            yesButton {
                presenter.deleteEvent()
                toast(R.string.event_deleted)
                finish()
            }
            noButton { it.dismiss() }
            isCancelable = false
        }.show()
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        presenter.searchGuest(newText)
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        presenter.searchGuest(query)
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)
        setSupportActionBar(toolbarEventDetail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setRecycler()
        val eventId = intent.getIntExtra("eventId", 0)
        presenter = EventDetailPresenter(this, this, eventId)
        presenter.getEventDetail()
        presenter.getEventGuest()
        onAction(eventId)
    }

    override fun onResume() {
        super.onResume()
        presenter.getEventDetail()
        presenter.getEventGuest()
    }

    private fun onAction(eventId: Int) {
        srlEventDetail.onRefresh { presenter.getEventGuest() }
        fabAddGuest.onClick { startActivity<AddGuestActivity>("eventId" to eventId) }
        svGuest.setOnQueryTextListener(this)
    }

    private fun setRecycler() {
        adapter = GuestAdapter(guest)
        rvGuest.setHasFixedSize(true)
        rvGuest.layoutManager = LinearLayoutManager(this)
        rvGuest.adapter = adapter
    }

    private fun onEmpty(guest: List<Guest?>) {
        if (guest.isEmpty()) {
            imgEmpty.visible()
            tvEmpty.visible()
        } else {
            imgEmpty.gone()
            tvEmpty.gone()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_edit, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
            R.id.navigation_edit -> startActivity<EditEventActivity>("event" to event)
            R.id.navigation_delete -> presenter.showDeleteAlert()
        }
        return super.onOptionsItemSelected(item)
    }
}
