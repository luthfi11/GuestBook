package com.luthfi.guestbook.ui.home

import android.content.Context
import com.luthfi.guestbook.data.db.database
import com.luthfi.guestbook.data.model.Event
import com.luthfi.guestbook.data.model.Guest
import org.jetbrains.anko.db.SqlOrderDirection
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

class HomePresenter(private val view: HomeView, private val ctx: Context?) {

    fun getLatestEvent() {
        ctx?.database?.use {
            val data = select(Event.TABlE_EVENT).orderBy(Event.ID, SqlOrderDirection.DESC).limit(1)
                .parseSingle(classParser<Event>())
            view.showLatestEvent(data)
        }
    }

    fun getGuestData(id: Int?) {
        view.showLoading()
        val listGuest = mutableListOf<Guest?>()
        ctx?.database?.use {
            try {
                val data = select(Guest.TABLE_GUEST).whereArgs("${Guest.EVENT_ID} = {eid}", "eid" to id!!).limit(5)
                    .parseList(classParser<Guest>())
                listGuest.addAll(data)
                listGuest.sortByDescending { it?.timeStamp }
                view.showGuestData(listGuest)
                view.hideLoading()
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    fun getMostAttended() {
        val query =
            "SELECT ${Guest.EVENT_ID}, COUNT(${Guest.EVENT_ID}) FROM ${Guest.TABLE_GUEST} GROUP BY ${Guest.EVENT_ID} ORDER BY COUNT(${Guest.EVENT_ID}) DESC LIMIT 3"
        val list = mutableListOf<Int>()
        ctx?.database?.use {
            val data = rawQuery(query, null)
            while (data.moveToNext()) {
                val id = data.getInt(0)
                list.add(id)
            }
            data.close()
        }

        val listEvent = mutableListOf<Event?>()
        ctx?.database?.use {
            try {
                val data = select(Event.TABlE_EVENT).parseList(classParser<Event>())
                listEvent.addAll(data)
                view.showMostAttended(listEvent)
            } finally {

            }
        }
    }
}