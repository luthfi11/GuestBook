package com.luthfi.guestbook.ui.guestdetail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.luthfi.guestbook.R
import com.luthfi.guestbook.data.model.Guest
import com.luthfi.guestbook.ui.editguest.EditGuestActivity
import com.luthfi.guestbook.util.DateUtil.timeFormat
import kotlinx.android.synthetic.main.activity_guest_detail.*
import org.jetbrains.anko.*

class GuestDetailActivity : AppCompatActivity(), GuestDetailView {

    private lateinit var presenter: GuestDetailPresenter
    private var guest: Guest? = null

    override fun showGuestDetail(guest: Guest?) {
        this.guest = guest

        tvName.text = guest?.name
        tvAddress.text = guest?.address
        tvEmail.text = guest?.email
        tvPhone.text = guest?.phone
        tvNote.text = guest?.guestNote
        tvTime.text = timeFormat("EEEE, dd MMMM yyyy, HH:mm", guest?.timeStamp?.toLong())
    }

    override fun showDeleteAlert() {
        alert {
            messageResource = R.string.delete_guest
            yesButton {
                presenter.deleteGuest()
                toast(R.string.guest_deleted)
                finish()
            }
            noButton { it.dismiss() }
            isCancelable = false
        }.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guest_detail)
        setSupportActionBar(toolbarGuest)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val guestId = intent.getIntExtra("guestId",0)
        presenter = GuestDetailPresenter(this, this, guestId)
        presenter.getGuestDetail()
    }

    override fun onResume() {
        super.onResume()
        presenter.getGuestDetail()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_edit, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
            R.id.navigation_edit -> startActivity<EditGuestActivity>("guest" to guest)
            R.id.navigation_delete -> presenter.showDeleteAlert()
        }
        return super.onOptionsItemSelected(item)
    }
}
