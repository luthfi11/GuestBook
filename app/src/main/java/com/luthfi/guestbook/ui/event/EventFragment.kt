package com.luthfi.guestbook.ui.event

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.luthfi.guestbook.R
import com.luthfi.guestbook.data.model.Event
import com.luthfi.guestbook.ui.addevent.AddEventActivity
import kotlinx.android.synthetic.main.fragment_event.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity

class EventFragment : Fragment(), EventView {

    private lateinit var presenter: EventPresenter
    private lateinit var adapter: EventAdapter
    private val event = mutableListOf<Event?>()

    override fun showLoading() {
        srlEvent.isRefreshing = true
    }

    override fun hideLoading() {
        srlEvent.isRefreshing = false
    }

    override fun showEventList(event: List<Event?>) {
        this.event.clear()
        this.event.addAll(event)
        adapter.notifyDataSetChanged()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_event, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setRecycler()
        onAction()
        presenter = EventPresenter(this, context)
        presenter.getEventList()
    }

    override fun onResume() {
        super.onResume()
        presenter.getEventList()
    }

    private fun setRecycler() {
        adapter = EventAdapter(event)
        rvEvent.setHasFixedSize(true)
        rvEvent.layoutManager = LinearLayoutManager(context)
        rvEvent.adapter = adapter
    }

    private fun onAction() {
        srlEvent.onRefresh { presenter.getEventList() }
        fabAdd.onClick { startActivity<AddEventActivity>() }
    }

}
