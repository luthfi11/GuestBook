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
            try {
                val data =
                    select(Event.TABlE_EVENT).orderBy(Event.ID, SqlOrderDirection.DESC).limit(1)
                        .parseList(classParser<Event>())
                if (data.isNotEmpty()) {
                    view.showLatestEvent(data[0])
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    fun getGuestData(id: Int?) {
        view.showLoading()
        val listGuest = mutableListOf<Guest?>()
        ctx?.database?.use {
            if (id != null) {
                val data =
                    select(Guest.TABLE_GUEST).whereArgs(
                        "${Guest.EVENT_ID} = {eid}",
                        "eid" to id
                    )
                        .limit(5)
                        .parseList(classParser<Guest>())

                listGuest.addAll(data)
                listGuest.sortByDescending { it?.timeStamp }
                view.showGuestData(listGuest)
                view.hideLoading()

                if (listGuest.isEmpty()) view.emptyGuest()

            } else {
                view.emptyNewestEvent()
                view.hideLoading()
            }
        }
    }

    fun getMostAttended() {
        val query =
            "SELECT * FROM ${Event.TABlE_EVENT} INNER JOIN ${Guest.TABLE_GUEST} " +
                    "ON ${Guest.TABLE_GUEST}.${Guest.EVENT_ID} = ${Event.TABlE_EVENT}.${Event.ID} " +
                    "GROUP BY ${Event.TABlE_EVENT}.${Event.ID} ORDER BY COUNT(*) DESC LIMIT 3"
        val list = mutableListOf<Event?>()
        ctx?.database?.use {
            val data = rawQuery(query, null)
            while (data.moveToNext()) {
                list.add(
                    Event(
                        data.getInt(0),
                        data.getString(1),
                        data.getString(2),
                        data.getString(3),
                        data.getString(4)
                    )
                )
            }
            view.showMostAttended(list)
            if (list.isEmpty()) view.emptyMostAttended()
            data.close()
        }
    }
}