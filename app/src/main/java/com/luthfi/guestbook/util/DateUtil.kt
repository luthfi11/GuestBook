package com.luthfi.guestbook.util

import android.app.DatePickerDialog
import android.content.Context
import android.text.format.DateUtils
import android.widget.EditText
import java.text.SimpleDateFormat
import java.util.*

object DateUtil {

    val locale = Locale("in", "ID")

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
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", locale)
            val date = dateFormat.format(chosenDate)
            editText.setText(date)

        }, year, month, day)

        dpd.datePicker.minDate = System.currentTimeMillis() - 1000
        dpd.show()
    }

    fun dateFormat(date: String?, pattern: String): String {
        val format = SimpleDateFormat("dd/MM/yyyy", locale)
        val sdf = SimpleDateFormat(pattern, locale)

        return sdf.format(format.parse(date))
    }

    fun dayAgo(millis: Long): String {
        return DateUtils.getRelativeTimeSpanString(
            millis,
            System.currentTimeMillis(),
            DateUtils.DAY_IN_MILLIS
        ).toString()
    }
}