package com.luthfi.guestbook.ui.home

import android.content.Context
import com.luthfi.guestbook.data.db.database
import com.luthfi.guestbook.data.model.Guest
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

class HomePresenter(private val view: HomeView, private val ctx: Context?) {

    fun getData() {
        view.showLoading()
        val listGuest = mutableListOf<Guest?>()
        ctx?.database?.use {
            val data = select(Guest.TABLE_GUEST).parseList(classParser<Guest>())
            listGuest.addAll(data)
            view.showData(listGuest)
            view.hideLoading()
        }
    }
}