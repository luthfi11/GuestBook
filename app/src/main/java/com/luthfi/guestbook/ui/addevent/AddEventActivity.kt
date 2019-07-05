package com.luthfi.guestbook.ui.addevent

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.luthfi.guestbook.R
import com.luthfi.guestbook.data.model.Event
import com.luthfi.guestbook.util.DateUtil
import com.luthfi.guestbook.util.LoadingDialog
import com.luthfi.guestbook.util.ValidateUtil.etToString
import com.luthfi.guestbook.util.ValidateUtil.validate
import kotlinx.android.synthetic.main.activity_add_event.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class AddEventActivity : AppCompatActivity(), AddEventView {

    private lateinit var presenter: AddEventPresenter

    override fun showLoading() {
        LoadingDialog.showLoading(this, R.string.adding_event)
    }

    override fun hideLoading() {
        LoadingDialog.hideLoading()
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_event)

        presenter = AddEventPresenter(this, this)

        onAction()
    }

    private fun onAction() {
        btnSetDate.setOnClickListener { DateUtil.datePicker(etEventDate, this) }
        btnSaveEvent.onClick { addEvent() }
    }

    private fun addEvent() {
        if ((validate(etEventDesc, getString(R.string.general_validate))) or
            (validate(etEventDate, getString(R.string.general_validate))) or
            (validate(etEventLocation, getString(R.string.general_validate))) or
            (validate(etEventName, getString(R.string.general_validate)))) {
            val event = Event(
                eventName = etToString(etEventName),
                eventLocation = etToString(etEventLocation),
                eventDate = etToString(etEventDate),
                eventDesc = etToString(etEventDesc)
            )
            presenter.addEvent(event)
        }
    }
}
