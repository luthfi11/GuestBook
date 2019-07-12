package com.luthfi.guestbook.ui.export

import com.luthfi.guestbook.data.model.Guest

interface ExportView {
    fun showLoading()
    fun hideLoading()
    fun showEventList(eventId: List<Int?>, eventName: List<String?>)
    fun showGuestList(guest: List<Guest?>)
    fun successExport(path: String)
    fun emptyListAler()
    fun showEmptyList()
}