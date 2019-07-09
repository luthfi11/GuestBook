package com.luthfi.guestbook.ui.export

import android.content.Context
import com.luthfi.guestbook.data.db.database
import com.luthfi.guestbook.data.model.Event
import com.luthfi.guestbook.data.model.Guest
import org.jetbrains.anko.db.IntParser
import org.jetbrains.anko.db.StringParser
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import java.io.FileWriter

class ExportPresenter(private val view: ExportView, private val ctx: Context?) {

    fun getEventList() {
        ctx?.database?.use {
            val id = select(Event.TABlE_EVENT, Event.ID).parseList(IntParser)
            val name = select(Event.TABlE_EVENT, Event.EVENT_NAME).parseList(StringParser)
            view.showEventList(id, name)
        }
    }

    fun getGuestList(eventId: Int?) {
        ctx?.database?.use {
            val data = select(Guest.TABLE_GUEST).whereArgs(
                "${Guest.EVENT_ID} = {eventId}",
                "eventId" to eventId!!).parseList(classParser<Guest>()
            )
            view.showGuestList(data)
        }
    }

    fun exportToCSV(eventName: String, list: List<Guest?>) {
        val fileWriter = FileWriter("$eventName.csv")
        fileWriter.append("No, Name, Address, Email, Phone, Guest Note")
        list.forEach {
            fileWriter.append(it?.name)
            fileWriter.append(it?.address)
            fileWriter.append(it?.email)
            fileWriter.append(it?.phone)
            fileWriter.append(it?.guestNote)
        }

        fileWriter.close()
    }
}