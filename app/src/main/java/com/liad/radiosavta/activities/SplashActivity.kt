package com.liad.radiosavta.activities

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import co.climacell.statefulLiveData.core.StatefulData
import com.liad.radiosavta.R
import com.liad.radiosavta.utils.extension.changeActivity
import com.liad.radiosavta.viewmodels.ProgramsViewModel
import org.koin.android.ext.android.inject

class SplashActivity : AppCompatActivity() {

    private val programsViewModel: ProgramsViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            setObservers()
        }, 1000)
    }

    private fun setObservers() {
        programsViewModel.getPrograms().observe(this, {
            if(it is StatefulData.Success) changeActivity(MainActivity::class.java, true)
        })
    }

}
