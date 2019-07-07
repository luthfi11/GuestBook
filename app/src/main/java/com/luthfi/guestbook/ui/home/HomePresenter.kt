package com.luthfi.guestbook.ui.home

import android.content.Context
import com.luthfi.guestbook.data.db.database
import com.luthfi.guestbook.data.model.Event
import com.luthfi.guestbook.data.model.Guest
import org.jetbrains.anko.db.SqlOrderDirection
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import java.lang.Exception

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
                val data = select(Guest.TABLE_GUEST).whereArgs("${Guest.EVENT_ID} = {eid}", "eid" to id!!).limit(8)
                    .parseList(classParser<Guest>())
                listGuest.addAll(data)
                view.showGuestData(listGuest)
                view.hideLoading()
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }
}