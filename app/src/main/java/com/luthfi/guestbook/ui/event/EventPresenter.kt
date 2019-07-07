package com.luthfi.guestbook.ui.event

import android.content.Context
import com.luthfi.guestbook.data.db.database
import com.luthfi.guestbook.data.model.Event
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

class EventPresenter(private val view: EventView, private val ctx: Context?) {

    fun getEventList() {
        view.showLoading()
        val listEvent = mutableListOf<Event?>()
        ctx?.database?.use {
            val data = select(Event.TABlE_EVENT).parseList(classParser<Event>())
            listEvent.addAll(data)
            listEvent.sortByDescending { it?.id }
            view.showEventList(listEvent)
            view.hideLoading()
        }
    }
}