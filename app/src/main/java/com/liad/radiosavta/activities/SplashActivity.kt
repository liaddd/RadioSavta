package com.liad.radiosavta.activities

import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import co.climacell.statefulLiveData.core.StatefulData
import com.liad.radiosavta.R
import com.liad.radiosavta.utils.extension.changeActivity
import com.liad.radiosavta.viewmodels.ProgramsViewModel
import kotlinx.android.synthetic.main.activity_splash.*
import org.koin.android.ext.android.inject

class SplashActivity : AppCompatActivity() {

    private val programsViewModel: ProgramsViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        setObservers()
    }

    override fun onResume() {
        super.onResume()
        startAnimation()
    }

    private fun startAnimation() {
        val animation = AnimationUtils.loadAnimation(this, R.anim.translate_animation)
        activity_splash_image_view.startAnimation(animation)
    }

    private fun setObservers() {
        programsViewModel.getCurrentPlayingSongTitle()
        programsViewModel.getPrograms().observe(this, Observer {
            when (it) {
                is StatefulData.Success -> {
                    Handler().postDelayed({
                        changeActivity(MainActivity::class.java, true)
                    }, 200)
                }
                is StatefulData.Loading -> {
                }
                is StatefulData.Error -> {
                }
            }
        })
    }

}
