package com.luthfi.guestbook.ui.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.luthfi.guestbook.R
import com.luthfi.guestbook.data.model.Event
import com.luthfi.guestbook.ui.addevent.AddEventActivity
import com.wysiwyg.temanolga.utilities.gone
import com.wysiwyg.temanolga.utilities.visible
import kotlinx.android.synthetic.main.fragment_event.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity

class EventFragment : Fragment(), EventView, SearchView.OnQueryTextListener {

    private lateinit var presenter: EventPresenter
    private lateinit var adapter: EventAdapter
    private val event = mutableListOf<Event?>()

    override fun showLoading() {
        clearSv()
        srlEvent.isRefreshing = true
    }

    override fun hideLoading() {
        srlEvent.isRefreshing = false
    }

    override fun showEventList(event: List<Event?>) {
        this.event.clear()
        this.event.addAll(event)
        adapter.notifyDataSetChanged()
        onEmpty(event)
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        presenter.searchEvent(newText)
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        presenter.searchEvent(query)
        return true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_event, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setRecycler()
        presenter = EventPresenter(this, context)
        presenter.getEventList()
        onAction()
    }

    override fun onResume() {
        super.onResume()
        presenter.getEventList()
    }

    private fun clearSv() {
        svEvent.setQuery("", false)
        svEvent.clearFocus()
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
        svEvent.setOnQueryTextListener(this)
    }

    private fun onEmpty(event: List<Event?>) {
        if (event.isEmpty()) {
            imgEmpty.visible()
            tvEmpty.visible()
        } else {
            imgEmpty.gone()
            tvEmpty.gone()
        }
    }

}
