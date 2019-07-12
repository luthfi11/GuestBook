package com.luthfi.guestbook.ui.export

import android.content.Context
import android.os.Environment.DIRECTORY_DOCUMENTS
import android.os.Environment.getExternalStorageDirectory
import com.luthfi.guestbook.data.db.database
import com.luthfi.guestbook.data.model.Event
import com.luthfi.guestbook.data.model.Guest
import com.luthfi.guestbook.util.CSVWriter
import kotlinx.coroutines.delay
import org.jetbrains.anko.db.IntParser
import org.jetbrains.anko.db.StringParser
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import java.io.File
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
                "eventId" to eventId!!
            ).parseList(
                classParser<Guest>()
            )

            if (data.isEmpty()) view.showEmptyList()
            else view.showGuestList(data)
        }
    }

    suspend fun exportToCSV(eventName: String, list: List<Guest?>) {
        if (list.isEmpty()) view.emptyListAler()
        else {
            view.showLoading()
            val exportDir: File? = File(getExternalStorageDirectory(), DIRECTORY_DOCUMENTS)
            if (!exportDir?.exists()!!) exportDir.mkdirs()
            val file = File(exportDir, "GUESTBOOK - $eventName.csv")
            try {
                file.createNewFile()
                var i = 1
                val csvWrite = CSVWriter(FileWriter(file))
                csvWrite.writeNext(arrayOf("No", "Name", "Address", "Email", "Phone", "Guest Note"))

                list.forEach {
                    csvWrite.writeNext(
                        arrayOf(
                            i.toString(),
                            it!!.name!!,
                            it.address!!,
                            it.email!!,
                            it.phone!!,
                            it.guestNote!!
                        )
                    )
                    i += 1
                }

                delay(400)
                view.hideLoading()
                view.successExport(file.path)
                csvWrite.close()
            } finally {

            }
        }
    }
}