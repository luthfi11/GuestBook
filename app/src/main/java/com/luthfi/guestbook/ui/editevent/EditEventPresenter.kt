package com.luthfi.guestbook.ui.editevent

import android.content.Context
import com.luthfi.guestbook.data.db.database
import com.luthfi.guestbook.data.model.Event
import org.jetbrains.anko.db.update

class EditEventPresenter(private val view: EditEventView, private val ctx: Context?, private val event: Event?) {

    fun showEventData() {
        view.showEventData(event)
    }

    fun editEvent(event: Event?) {
        view.showLoading()
        ctx?.database?.use {
            update(
                Event.TABlE_EVENT,
                Event.EVENT_NAME to event?.eventName,
                Event.EVENT_LOCATION to event?.eventLocation,
                Event.EVENT_DESC to event?.eventDesc,
                Event.EVENT_DATE to event?.eventDate
            )
                .whereArgs("${Event.ID} = {eventId}", "eventId" to event?.id!!)
                .exec()
            view.hideLoading()
        }
    }
}