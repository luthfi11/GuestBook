package com.luthfi.guestbook.ui.editevent

import com.luthfi.guestbook.data.model.Event

interface EditEventView {
    fun showEventData(event: Event?)
    fun showLoading()
    fun hideLoading()
}