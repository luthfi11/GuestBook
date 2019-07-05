package com.luthfi.guestbook.ui.home

import com.luthfi.guestbook.data.model.Guest

interface HomeView {
    fun showLoading()
    fun hideLoading()
    fun showData(guest: List<Guest?>)
}