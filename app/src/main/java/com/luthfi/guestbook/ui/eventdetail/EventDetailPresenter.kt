package com.luthfi.guestbook.ui.eventdetail

import android.content.Context
import com.luthfi.guestbook.data.db.database
import com.luthfi.guestbook.data.model.Event
import com.luthfi.guestbook.data.model.Guest
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.select

class EventDetailPresenter(private val view: EventDetailView, private val ctx: Context?, private val eventId: Int) {

    fun getEventDetail() {
        ctx?.database?.use {
            val data = select(Event.TABlE_EVENT).whereArgs("${Event.ID} = {eid}", "eid" to eventId)
                .parseSingle(classParser<Event>())
            view.showEventDetail(data)
        }
    }

    fun getEventGuest() {
        view.showLoading()
        val listGuest = mutableListOf<Guest?>()
        ctx?.database?.use {
            val data = select(Guest.TABLE_GUEST).whereArgs(
                "${Guest.EVENT_ID} = {eventId}",
                "eventId" to eventId
            ).parseList(classParser<Guest>())
            listGuest.addAll(data)
            listGuest.sortByDescending { it?.id }
            view.showEventGuest(listGuest)
            view.hideLoading()
        }
    }

    fun searchGuest(name: String?) {
        val listGuest = mutableListOf<Guest?>()
        ctx?.database?.use {
            val data = select(Guest.TABLE_GUEST).whereArgs(
                "${Guest.EVENT_ID} = {eventId}",
                "eventId" to eventId
            ).parseList(classParser<Guest>())
            listGuest.addAll(data)
            listGuest.sortByDescending { it?.id }
            view.showEventGuest(listGuest.filter { it?.name?.contains(name!!, true)!! })
        }
    }

    fun showDeleteAlert() {
        view.showDeleteAlert()
    }

    fun deleteEvent() {
        ctx?.database?.use {
            delete(Event.TABlE_EVENT, "${Event.ID} = {eventId}", "eventId" to eventId).also {
                delete(Guest.TABLE_GUEST, "${Guest.EVENT_ID} = {eventId}", "eventId" to eventId)
            }
        }
    }
}