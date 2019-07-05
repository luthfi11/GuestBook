package com.luthfi.guestbook.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Event(
    val id: Int? = null,
    val eventName: String? = null,
    val eventLocation: String? = null,
    val eventDesc: String? = null,
    val eventDate: String? = null
): Parcelable {
    companion object {
        const val TABlE_EVENT: String = "TABLE_EVENT"
        const val ID: String = "ID_"
        const val EVENT_NAME: String = "EVENT_NAME"
        const val EVENT_LOCATION: String = "EVENT_LOCATION"
        const val EVENT_DESC: String = "EVENT_DESC"
        const val EVENT_DATE: String = "EVENT_DATE"
    }
}