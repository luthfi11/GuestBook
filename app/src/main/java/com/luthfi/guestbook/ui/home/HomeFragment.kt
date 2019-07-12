package com.luthfi.guestbook.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.luthfi.guestbook.R
import com.luthfi.guestbook.data.model.Event
import com.luthfi.guestbook.data.model.Guest
import com.luthfi.guestbook.ui.event.EventAdapter
import com.luthfi.guestbook.ui.eventdetail.EventDetailActivity
import com.luthfi.guestbook.ui.eventdetail.GuestAdapter
import com.luthfi.guestbook.util.DateUtil.dateFormat
import com.wysiwyg.temanolga.utilities.gone
import com.wysiwyg.temanolga.utilities.visible
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity

class HomeFragment : Fragment(), HomeView {

    private lateinit var presenter: HomePresenter
    private lateinit var adapter: GuestAdapter
    private lateinit var eventAdapter: EventAdapter
    private val guest = mutableListOf<Guest?>()
    private val event = mutableListOf<Event?>()
    private var id: Int? = null

    override fun showLoading() {
        srlHome.isRefreshing = true
    }

    override fun hideLoading() {
        srlHome.isRefreshing = false
    }

    override fun showLatestEvent(event: Event?) {
        this.id = event?.id
        tvLatestEvent.text = event?.eventName
        tvLatestEventDate.text = dateFormat(event?.eventDate, "dd MMMM yyyy")
    }

    override fun showGuestData(guest: List<Guest?>) {
        this.guest.clear()
        this.guest.addAll(guest)
        adapter.notifyDataSetChanged()
    }

    override fun showMostAttended(event: List<Event?>) {
        this.event.clear()
        this.event.addAll(event)
        eventAdapter.notifyDataSetChanged()
    }

    override fun emptyNewestEvent() {
        tvLatestEvent.gone()
        tvLatestEventDate.gone()
        tvEmptyAttended.gone()
        btnSeeAll.gone()
        tvEmptyNewest.visible()
    }

    override fun emptyMostAttended() {
        tvEmptyAttended.visible()
    }

    override fun emptyGuest() {
        btnSeeAll.gone()
        tvEmptyGuest.visible()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setRecycler()
        onAction()
        presenter = HomePresenter(this, context)
        presenter.getLatestEvent()
        presenter.getGuestData(id)
        presenter.getMostAttended()
    }

    private fun setRecycler() {
        adapter = GuestAdapter(guest)
        rvLatestGuest.setHasFixedSize(true)
        rvLatestGuest.layoutManager = LinearLayoutManager(context)
        rvLatestGuest.adapter = adapter

        eventAdapter = EventAdapter(event)
        rvMostAttended.setHasFixedSize(true)
        rvMostAttended.layoutManager = LinearLayoutManager(context)
        rvMostAttended.adapter = eventAdapter
    }

    private fun onAction() {
        srlHome.onRefresh { presenter.getGuestData(id) }
        btnSeeAll.onClick { startActivity<EventDetailActivity>("eventId" to id) }
    }
}
