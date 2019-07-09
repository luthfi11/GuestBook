package com.luthfi.guestbook.ui.export

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.luthfi.guestbook.R
import com.luthfi.guestbook.data.model.Guest
import kotlinx.android.synthetic.main.fragment_export.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class ExportFragment : Fragment(), ExportView {

    private lateinit var presenter: ExportPresenter
    private lateinit var adapter: ExportAdapter
    private val guest = mutableListOf<Guest?>()

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showEventList(eventId: List<Int?>, eventName: List<String?>) {
        spnEvent.adapter = ArrayAdapter(context!!, R.layout.spinner_item, eventName)
        spnEvent.onItemSelectedListener = spinnerSelection(eventId)
    }

    override fun showGuestList(guest: List<Guest?>) {
        this.guest.clear()
        this.guest.addAll(guest)
        adapter.notifyDataSetChanged()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_export, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setRecycler()
        presenter = ExportPresenter(this, context)
        presenter.getEventList()

        btnExport.onClick { presenter.exportToCSV("HAHA", guest) }
    }

    private fun setRecycler() {
        adapter = ExportAdapter(guest)
        rvGuest.setHasFixedSize(true)
        rvGuest.layoutManager = LinearLayoutManager(context)
        rvGuest.adapter = adapter
    }

    private fun spinnerSelection(eid: List<Int?>) = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {}

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            presenter.getGuestList(eid[position])
        }
    }
}