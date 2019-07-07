package com.luthfi.guestbook.ui.addguest

import android.content.Context
import com.luthfi.guestbook.data.db.database
import com.luthfi.guestbook.data.model.Guest
import org.jetbrains.anko.db.insert

class AddGuestPresenter(private val view: AddGuestView, private val ctx: Context?) {

    fun addGuest(guest: Guest?, eventId: Int) {
        view.showLoading()
        ctx?.database?.use {
            insert(Guest.TABLE_GUEST,
                Guest.NAME to guest?.name,
                Guest.EMAIL to guest?.email,
                Guest.PHONE to guest?.phone,
                Guest.ADDRESS to guest?.address,
                Guest.GUEST_NOTE to guest?.guestNote,
                Guest.TIMESTAMP to System.currentTimeMillis().toString(),
                Guest.EVENT_ID to eventId
                )
            view.hideLoading()
        }
    }
}