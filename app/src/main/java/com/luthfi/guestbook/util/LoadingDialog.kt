package com.luthfi.guestbook.util

import android.app.Dialog
import android.content.Context
import android.view.Window
import com.bumptech.glide.Glide
import com.luthfi.guestbook.R
import kotlinx.android.synthetic.main.loading_dialog.*

object LoadingDialog {

    private lateinit var dialog: Dialog

    fun showLoading(context: Context, title: Int) {
        dialog = Dialog(context).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.loading_dialog)
            setCancelable(false)
        }

        dialog.textView.text = context.resources.getString(title)
        Glide.with(context).asGif().load(R.drawable.ic_loading).into(dialog.progressBar)
        dialog.show()
    }

    fun hideLoading() {
        dialog.dismiss()
    }
}