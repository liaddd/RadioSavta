package com.liad.radiosavta.fragments


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.liad.radiosavta.R
import com.liad.radiosavta.RadioSavtaApplication
import com.liad.radiosavta.utils.extension.sendEvent
import kotlinx.android.synthetic.main.fragment_patron.*


class DonationFragment : Fragment() {

    companion object {
        fun newInstance(): DonationFragment = DonationFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = inflater.inflate(R.layout.fragment_patron, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        fragment_donation_donate_button?.setOnClickListener { becomePatron() }
    }

    private fun becomePatron() {
        RadioSavtaApplication.sTracker?.sendEvent("radiosavta-android" , "patreon.click")
        val uri = Uri.parse("https://www.patreon.com/radiosavta")
        startActivity(Intent(Intent.ACTION_VIEW , uri) , null)
    }
}
