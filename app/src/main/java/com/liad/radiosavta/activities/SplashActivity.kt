package com.liad.radiosavta.activities

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import co.climacell.statefulLiveData.core.StatefulData
import com.liad.radiosavta.R
import com.liad.radiosavta.utils.extension.changeActivity
import com.liad.radiosavta.utils.extension.toast
import com.liad.radiosavta.viewmodels.ProgramsViewModel
import org.koin.android.ext.android.inject

class SplashActivity : AppCompatActivity() {

    private val programsViewModel: ProgramsViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        setObservers()
    }

    private fun setObservers() {
        programsViewModel.getCurrentPlayingSongTitle()
        programsViewModel.getPrograms().observe(this, Observer {
            when (it) {
                is StatefulData.Success -> {
                    Handler().postDelayed({
                        changeActivity(MainActivity::class.java, true)
                    }, 1000)
                }
                is StatefulData.Loading -> {
                }
                is StatefulData.Error -> {
                    toast(this, "Something went wrong O_o")
                }
            }
        })
    }

}
