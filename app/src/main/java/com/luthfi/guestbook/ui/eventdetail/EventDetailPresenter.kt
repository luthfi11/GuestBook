package com.luthfi.guestbook.ui.eventdetail

import android.content.Context
import com.luthfi.guestbook.data.db.database
import com.luthfi.guestbook.data.model.Event
import com.luthfi.guestbook.data.model.Guest
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

class EventDetailPresenter(private val view: EventDetailView, private val ctx: Context?, private val event: Event?) {

    fun getEventDetail() {
        view.showEventDetail(event)
    }

    fun getEventGuest() {
        view.showLoading()
        val listGuest = mutableListOf<Guest?>()
        ctx?.database?.use {
            val data = select(Guest.TABLE_GUEST).whereArgs(
                "${Guest.EVENT_ID} like %{eventId}%",
                "eventId" to event?.id.toString()
            ).parseList(classParser<Guest>())
            listGuest.addAll(data)
            view.showEventGuest(listGuest)
            view.hideLoading()
        }
    }
}