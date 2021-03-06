package com.luthfi.guestbook.ui.addguest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.luthfi.guestbook.R
import com.luthfi.guestbook.data.model.Guest
import com.luthfi.guestbook.util.LoadingDialog
import com.luthfi.guestbook.util.ValidateUtil.emailValidate
import com.luthfi.guestbook.util.ValidateUtil.etToString
import com.luthfi.guestbook.util.ValidateUtil.validate
import kotlinx.android.synthetic.main.activity_add_guest.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class AddGuestActivity : AppCompatActivity(), AddGuestView {

    private lateinit var presenter: AddGuestPresenter
    private var eventId: Int = 0

    override fun showLoading() {
        LoadingDialog.showLoading(this, R.string.loading)
    }

    override fun hideLoading() {
        LoadingDialog.hideLoading()
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_guest)
        setSupportActionBar(toolbarAddGuest)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        presenter = AddGuestPresenter(this, this)
        eventId = intent.getIntExtra("eventId", 0)
        btnSaveGuest.onClick { addGuest() }
    }

    private fun addGuest() {
        if ((validate(etAddress, getString(R.string.general_validate))) or
            (validate(etPhone, getString(R.string.general_validate))) or
            (validate(etEmail, getString(R.string.general_validate))) or
            (validate(etGuestName, getString(R.string.general_validate)))
        ) {

            val guest = Guest(
                name = etToString(etGuestName),
                email = etToString(etEmail),
                phone = etToString(etPhone),
                address = etToString(etAddress),
                guestNote = etToString(etGuestNote)
            )

            emailValidate(etEmail, getString(R.string.email_validate)) {
                presenter.addGuest(guest, eventId)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }
}
