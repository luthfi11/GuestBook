package com.luthfi.guestbook.ui.home

import com.luthfi.guestbook.data.model.Event
import com.luthfi.guestbook.data.model.Guest

interface HomeView {
    fun showLoading()
    fun hideLoading()
    fun showLatestEvent(event: Event?)
    fun showGuestData(guest: List<Guest?>)
    fun showMostAttended(event: List<Event?>)
}