package com.luthfi.guestbook.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.luthfi.guestbook.data.model.Event
import com.luthfi.guestbook.data.model.Guest
import org.jetbrains.anko.db.*

class DatabaseOpenHelper(context: Context) : ManagedSQLiteOpenHelper(context, "GuestBook.db", null, 1) {

    companion object {
        private var instance: DatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(context: Context): DatabaseOpenHelper {
            if (instance == null) {
                instance = DatabaseOpenHelper(context.applicationContext)
            }
            return instance as DatabaseOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(
            Event.TABlE_EVENT, true,
            Event.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            Event.EVENT_NAME to TEXT,
            Event.EVENT_LOCATION to TEXT,
            Event.EVENT_DESC to TEXT,
            Event.EVENT_DATE to TEXT
        )

        db?.createTable(
            Guest.TABLE_GUEST, true,
            Guest.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            Guest.NAME to TEXT,
            Guest.ADDRESS to TEXT,
            Guest.PHONE to TEXT,
            Guest.EMAIL to TEXT,
            Guest.GUEST_NOTE to TEXT,
            Guest.TIMESTAMP to TEXT,
            Guest.EVENT_ID to INTEGER
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable(Event.TABlE_EVENT, true)
        db?.dropTable(Guest.TABLE_GUEST, true)
    }
}

val Context.database: DatabaseOpenHelper
    get() = DatabaseOpenHelper.getInstance(applicationContext)