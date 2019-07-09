package com.luthfi.guestbook.ui.editguest

import android.content.Context
import com.luthfi.guestbook.data.db.database
import com.luthfi.guestbook.data.model.Guest
import org.jetbrains.anko.db.update

class EditGuestPresenter(private val view: EditGuestView, private val ctx: Context?) {

    fun getGuestData(guest: Guest?) {
        view.showGuestData(guest)
    }

    fun updateGuest(guest: Guest?) {
        view.showLoading()
        ctx?.database?.use {
            update(
                Guest.TABLE_GUEST,
                Guest.ID to guest?.id,
                Guest.NAME to guest?.name,
                Guest.EMAIL to guest?.email,
                Guest.PHONE to guest?.phone,
                Guest.ADDRESS to guest?.address,
                Guest.GUEST_NOTE to guest?.guestNote,
                Guest.TIMESTAMP to guest?.timeStamp,
                Guest.EVENT_ID to guest?.eventId
            )
                .whereArgs("${Guest.ID} = {id}", "id" to guest?.id!!)
                .exec()
            view.hideLoading()
        }
    }
}