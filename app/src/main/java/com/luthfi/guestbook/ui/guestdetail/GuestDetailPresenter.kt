package com.luthfi.guestbook.ui.guestdetail

import android.content.Context
import com.luthfi.guestbook.data.db.database
import com.luthfi.guestbook.data.model.Guest
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.select

class GuestDetailPresenter(private val view: GuestDetailView, private val ctx: Context?, private val guestId: Int) {

    fun getGuestDetail() {
        ctx?.database?.use {
            val data = select(Guest.TABLE_GUEST)
                .whereArgs("${Guest.ID} = {id}", "id" to guestId)
                .parseSingle(classParser<Guest>())
            view.showGuestDetail(data)
        }
    }

    fun showDeleteAlert() {
        view.showDeleteAlert()
    }

    fun deleteGuest() {
        ctx?.database?.use {
            delete(Guest.TABLE_GUEST, "${Guest.ID} = {id}", "id" to guestId)
        }
    }
}