package com.luthfi.guestbook.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.luthfi.guestbook.R
import com.luthfi.guestbook.data.model.Guest
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.support.v4.onRefresh

class HomeFragment : Fragment(), HomeView {

    private lateinit var presenter: HomePresenter
    private lateinit var adapter: GuestAdapter
    private val guest = mutableListOf<Guest?>()

    override fun showLoading() {
        srlHome.isRefreshing = true
    }

    override fun hideLoading() {
        srlHome.isRefreshing = false
    }

    override fun showData(guest: List<Guest?>) {
        this.guest.clear()
        this.guest.addAll(guest)
        adapter.notifyDataSetChanged()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setRecycler()
        onAction()
        presenter = HomePresenter(this, context)
        presenter.getData()
    }

    private fun setRecycler() {
        adapter = GuestAdapter(guest)
        rvHome.setHasFixedSize(true)
        rvHome.layoutManager = LinearLayoutManager(context)
        rvHome.adapter = adapter
    }

    private fun onAction() {
        srlHome.onRefresh { presenter.getData() }
    }
}
