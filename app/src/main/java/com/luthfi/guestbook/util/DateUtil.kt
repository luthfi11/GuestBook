package com.luthfi.guestbook.util

import android.app.DatePickerDialog
import android.content.Context
import android.widget.EditText
import java.text.SimpleDateFormat
import java.util.*

object DateUtil {

    fun datePicker(editText: EditText, context: Context) {
        val c = Calendar.getInstance()
        val day = c.get(Calendar.DAY_OF_MONTH)
        val month = c.get(Calendar.MONTH)
        val year = c.get(Calendar.YEAR)

        val dpd = DatePickerDialog(context, DatePickerDialog.OnDateSetListener {
                _, years, monthOfYear, dayOfMonth ->

            c.timeInMillis = 0
            c.set(years, monthOfYear, dayOfMonth, 0, 0, 0)
            val chosenDate = c.time
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val date = dateFormat.format(chosenDate)
            editText.setText(date)

        }, year, month, day)

        dpd.datePicker.minDate = System.currentTimeMillis() - 1000
        dpd.show()
    }

    fun dateFormat(date: String?, pattern: String): String {
        val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())

        return sdf.format(format.parse(date))
    }

    fun timeFormat(format: String, millis: Long?): String {
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        return sdf.format(Date(millis!!))
    }
}