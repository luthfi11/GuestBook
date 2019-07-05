package com.luthfi.guestbook.ui.event

import com.luthfi.guestbook.data.model.Event

interface EventView {
    fun showLoading()
    fun hideLoading()
    fun showEventList(event: List<Event?>)
}