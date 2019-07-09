package com.luthfi.guestbook.ui.editguest

import com.luthfi.guestbook.data.model.Guest

interface EditGuestView {
    fun showGuestData(guest: Guest?)
    fun showLoading()
    fun hideLoading()
}