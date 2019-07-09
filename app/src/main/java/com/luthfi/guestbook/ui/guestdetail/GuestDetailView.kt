package com.luthfi.guestbook.ui.guestdetail

import com.luthfi.guestbook.data.model.Guest

interface GuestDetailView {
    fun showGuestDetail(guest: Guest?)
    fun showDeleteAlert()
}