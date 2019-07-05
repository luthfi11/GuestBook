package com.luthfi.guestbook.ui.addevent

import android.content.Context
import com.luthfi.guestbook.data.db.database
import com.luthfi.guestbook.data.model.Event
import org.jetbrains.anko.db.insert

class AddEventPresenter(private val view: AddEventView, private val ctx: Context?) {

    fun addEvent(event: Event?) {
        view.showLoading()
        ctx?.database?.use {
            insert(Event.TABlE_EVENT,
                Event.EVENT_NAME to event?.eventName,
                Event.EVENT_LOCATION to event?.eventLocation,
                Event.EVENT_DESC to event?.eventDesc,
                Event.EVENT_DATE to event?.eventDate)
        }.also {
            view.hideLoading()
        }
    }
}