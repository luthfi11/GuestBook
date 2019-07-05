package com.luthfi.guestbook.util

import android.util.Patterns
import android.widget.EditText
import android.widget.Spinner

object ValidateUtil {

    fun etToString(editText: EditText): String {
        return editText.text.toString()
    }

    fun setError(editText: EditText, msg: String) {
        editText.requestFocus()
        editText.error = msg
    }

    fun emailValidate(etEmail: EditText, msg: String, listener: () -> Unit) {
        if (Patterns.EMAIL_ADDRESS.matcher(etToString(etEmail)).matches()) listener()
        else setError(etEmail, msg)
    }

    fun etValidate(editText: EditText, msg: String, listener: () -> Unit) {
        if (etToString(editText).isNotEmpty()) listener()
        else setError(editText, msg)
    }

    fun validate(editText: EditText, msg: String) : Boolean {
        return if (etToString(editText).isNotEmpty()) true
        else {
            setError(editText, msg)
            false
        }
    }

    fun spnPosition(spinner: Spinner): String {
        return spinner.selectedItemPosition.toString()
    }
}