package com.luthfi.guestbook.ui.editguest

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.luthfi.guestbook.R
import com.luthfi.guestbook.data.model.Guest
import com.luthfi.guestbook.util.LoadingDialog
import com.luthfi.guestbook.util.ValidateUtil.emailValidate
import com.luthfi.guestbook.util.ValidateUtil.etToString
import com.luthfi.guestbook.util.ValidateUtil.validate
import kotlinx.android.synthetic.main.activity_add_guest.*
import org.jetbrains.anko.appcompat.v7.titleResource
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.toast

class EditGuestActivity : AppCompatActivity(), EditGuestView {

    private lateinit var presenter: EditGuestPresenter
    private var guest: Guest? = null

    override fun showGuestData(guest: Guest?) {
        etGuestName.setText(guest?.name)
        etAddress.setText(guest?.address)
        etEmail.setText(guest?.email)
        etPhone.setText(guest?.phone)
        etGuestNote.setText(guest?.guestNote)

        this.guest = guest
    }

    override fun showLoading() {
        LoadingDialog.showLoading(this, R.string.updating)
    }

    override fun hideLoading() {
        LoadingDialog.hideLoading()
        toast(R.string.guest_updated)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_guest)
        setSupportActionBar(toolbarAddGuest)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbarAddGuest?.titleResource = R.string.edit_guest

        val guest = intent?.getParcelableExtra<Guest>("guest")
        presenter = EditGuestPresenter(this, this)
        presenter.getGuestData(guest)

        btnSaveGuest.onClick { editGuest() }
    }

    private fun editGuest() {
        if ((validate(etAddress, getString(R.string.general_validate))) or
            (validate(etPhone, getString(R.string.general_validate))) or
            (validate(etEmail, getString(R.string.general_validate))) or
            (validate(etGuestName, getString(R.string.general_validate)))
        ) {

            val guest = Guest(
                id = guest?.id,
                name = etToString(etGuestName),
                email = etToString(etEmail),
                phone = etToString(etPhone),
                address = etToString(etAddress),
                guestNote = etToString(etGuestNote),
                timeStamp = guest?.timeStamp,
                eventId = guest?.eventId
            )

            emailValidate(etEmail, getString(R.string.email_validate)) {
                presenter.updateGuest(guest)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }
}
