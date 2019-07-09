package com.luthfi.guestbook.ui.editevent

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.luthfi.guestbook.R
import com.luthfi.guestbook.data.model.Event
import com.luthfi.guestbook.util.DateUtil
import com.luthfi.guestbook.util.LoadingDialog
import com.luthfi.guestbook.util.ValidateUtil
import kotlinx.android.synthetic.main.activity_add_event.*
import org.jetbrains.anko.appcompat.v7.titleResource
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.toast

class EditEventActivity : AppCompatActivity(), EditEventView {

    private lateinit var presenter: EditEventPresenter
    private var event: Event? = null
    private var id: Int? = null

    override fun showEventData(event: Event?) {
        etEventName.setText(event?.eventName)
        etEventDate.setText(event?.eventDate)
        etEventLocation.setText(event?.eventLocation)
        etEventDesc.setText(event?.eventDesc)

        this.id = event?.id
    }

    override fun showLoading() {
        LoadingDialog.showLoading(this, R.string.updating)
    }

    override fun hideLoading() {
        LoadingDialog.hideLoading()
        toast(R.string.event_updated)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_event)
        setSupportActionBar(toolbarAddEvent)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbarAddEvent.titleResource = R.string.edit_event

        event = intent?.getParcelableExtra("event")
        presenter = EditEventPresenter(this, this, event)
        presenter.showEventData()

        onAction()
    }

    private fun onAction() {
        btnSetDate.setOnClickListener { DateUtil.datePicker(etEventDate, this) }
        btnSaveEvent.onClick { editEvent() }
    }

    private fun editEvent() {
        if ((ValidateUtil.validate(etEventDesc, getString(R.string.general_validate))) or
            (ValidateUtil.validate(etEventDate, getString(R.string.general_validate))) or
            (ValidateUtil.validate(etEventLocation, getString(R.string.general_validate))) or
            (ValidateUtil.validate(etEventName, getString(R.string.general_validate)))) {
            val newEvent = Event(
                id = id,
                eventName = ValidateUtil.etToString(etEventName),
                eventLocation = ValidateUtil.etToString(etEventLocation),
                eventDate = ValidateUtil.etToString(etEventDate),
                eventDesc = ValidateUtil.etToString(etEventDesc)
            )
            presenter.editEvent(newEvent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }
}
