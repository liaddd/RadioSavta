package com.liad.radiosavta.activities

import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.liad.radiosavta.R
import com.liad.radiosavta.adapters.MainFragmentAdapter
import com.liad.radiosavta.utils.PlayAudioManager
import com.liad.radiosavta.viewmodels.ProgramsViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity(), TabLayout.OnTabSelectedListener, View.OnClickListener {

    private lateinit var fragmentAdapter: MainFragmentAdapter
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2

    private val programsViewModel : ProgramsViewModel by inject()
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen()
        setContentView(R.layout.activity_main)

        initViews()
        setObservers()
    }


    override fun onTabSelected(tab: TabLayout.Tab?) {
        tab?.let { changeTabState(it) }
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
        tab?.let { changeTabState(it) }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
        tab?.let { changeTabState(it) }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.main_activity_play_image_view -> {
                onPlayPauseClicked()
            }
        }
    }

    private fun initViews() {
        fragmentAdapter = MainFragmentAdapter(this)

        mediaPlayer = PlayAudioManager.initMediaPlayer(this)
        main_activity_play_image_view.setOnClickListener(this)

        viewPager = main_activity_view_pager.apply {
            adapter = fragmentAdapter
        }

        initTabLayout()

    }

    private fun initTabLayout() {
        tabLayout = main_activity_tab_layout

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            setupTabs(tab, position)
        }.attach()

        tabLayout.addOnTabSelectedListener(this)
    }

    private fun setObservers() {
        programsViewModel.getPrograms()
        programsViewModel.getCurrentPlayingSongTitle()
    }

    private fun onPlayPauseClicked() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.pause()
            } else {
                tabLayout.getTabAt(2)?.select()
                it.start()
            }
            handleMediaPlayerState()
        }
    }

    private fun setFullScreen() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    private fun setupTabs(tab: TabLayout.Tab, position: Int) {
        when (position) {
            0 -> {
                tab.setIcon(R.drawable.ic_home_green_24dp)
            }
            1 -> {
                tab.setIcon(R.drawable.ic_music_note_white_24dp)
            }
            3 -> {
                tab.setIcon(R.drawable.ic_people_white_24dp)
            }
            4 -> {
                tab.setIcon(R.drawable.ic_settings_white_24dp)
            }
        }
    }

    private fun changeTabState(tab: TabLayout.Tab) {
        when (tab.position) {
            0 -> {
                tab.setIcon(if (tab.isSelected) R.drawable.ic_home_green_24dp else R.drawable.ic_home_white_24dp)
            }
            1 -> {
                tab.setIcon(if (tab.isSelected) R.drawable.ic_music_note_green_24dp else R.drawable.ic_music_note_white_24dp)
            }
            2 -> {
                handleMediaPlayerState()
            }
            3 -> {
                tab.setIcon(if (tab.isSelected) R.drawable.ic_people_green_24dp else R.drawable.ic_people_white_24dp)
            }
            4 -> {
                tab.setIcon(if (tab.isSelected) R.drawable.ic_settings_green_24dp else R.drawable.ic_settings_white_24dp)
            }
        }
    }

    private fun handleMediaPlayerState() {
        if (mediaPlayer == null) {
            PlayAudioManager.playAudio()
        }
        mediaPlayer?.let {
            main_activity_play_image_view.setImageResource(if (it.isPlaying) R.drawable.pause_button_background else R.drawable.play_button_background)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.stop()
    }

}
