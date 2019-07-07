package com.luthfi.guestbook.ui.eventdetail

import com.luthfi.guestbook.data.model.Event
import com.luthfi.guestbook.data.model.Guest

interface EventDetailView {
    fun showLoading()
    fun hideLoading()
    fun showEventDetail(event: Event?)
    fun showEventGuest(guest: List<Guest?>)
    fun showDeleteAlert()
}