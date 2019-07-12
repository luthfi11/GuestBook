package com.luthfi.guestbook.ui.export

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.luthfi.guestbook.R
import com.luthfi.guestbook.data.model.Guest
import com.luthfi.guestbook.util.LoadingDialog
import com.wysiwyg.temanolga.utilities.gone
import com.wysiwyg.temanolga.utilities.visible
import kotlinx.android.synthetic.main.fragment_export.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.longToast
import org.jetbrains.anko.yesButton

class ExportFragment : Fragment(), ExportView {

    private lateinit var presenter: ExportPresenter
    private lateinit var adapter: ExportAdapter
    private val guest = mutableListOf<Guest?>()

    override fun showLoading() {
        LoadingDialog.showLoading(context!!, R.string.exporting)
    }

    override fun hideLoading() {
        LoadingDialog.hideLoading()
    }

    override fun showEmptyList() {
        tvEmptyGuest.visible()
        rvGuest.gone()
    }

    override fun showEventList(eventId: List<Int?>, eventName: List<String?>) {
        spnEvent.adapter = ArrayAdapter(context!!, R.layout.spinner_item, eventName)
        spnEvent.onItemSelectedListener = spinnerSelection(eventId)
    }

    override fun emptyListAler() {
        alert {
            messageResource = R.string.fail_export_msg
            isCancelable = false
            yesButton { it.dismiss() }
        }.show()
    }

    override fun showGuestList(guest: List<Guest?>) {
        tvEmptyGuest.gone()
        rvGuest.visible()
        this.guest.clear()
        this.guest.addAll(guest)
        adapter.notifyDataSetChanged()
    }

    override fun successExport(path: String) {
        longToast("File saved in $path")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_export, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setRecycler()
        presenter = ExportPresenter(this, context)
        presenter.getEventList()

        btnExport.onClick { exportToCsv() }
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

    private suspend fun exportToCsv() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(context!!, WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                presenter.exportToCSV(spnEvent.selectedItem.toString(), guest)
            else
                ActivityCompat.requestPermissions(activity!!, arrayOf(WRITE_EXTERNAL_STORAGE), 2)

        } else presenter.exportToCSV(spnEvent.selectedItem.toString(), guest)
    }
}