package com.luthfi.guestbook.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Guest(
    val id: Int? = null,
    val name: String? = null,
    val address: String? = null,
    val phone: String? = null,
    val email: String? = null,
    val guestNote: String? = null,
    val timeStamp: String? = null,
    val eventId: Int? = null
) : Parcelable {
    companion object {
        const val TABLE_GUEST: String = "TABLE_GUEST"
        const val ID: String = "ID_"
        const val NAME: String = "NAME"
        const val ADDRESS: String = "ADDRESS"
        const val PHONE: String = "PHONE"
        const val EMAIL: String = "EMAIL"
        const val GUEST_NOTE: String = "GUEST_NOTE"
        const val TIMESTAMP: String = "TIMESTAMP"
        const val EVENT_ID: String = "EVENT_ID"
    }
}