package com.liad.radiosavta.activities

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.liad.radiosavta.R
import com.liad.radiosavta.adapters.MainFragmentAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), TabLayout.OnTabSelectedListener, View.OnClickListener {

    private lateinit var fragmentAdapter: MainFragmentAdapter
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen()
        setContentView(R.layout.activity_main)

        initViews()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.main_activity_play_image_view -> {
                tabLayout.getTabAt(2)?.select()
            }
        }
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        tab?.let { changeTabState(it) }
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {

    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
        tab?.let { changeTabState(it) }
    }

    private fun setFullScreen() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    private fun initViews() {
        fragmentAdapter = MainFragmentAdapter(this)
        tabLayout = main_activity_tab_layout

        val viewPager = main_activity_view_pager.apply {
            adapter = fragmentAdapter
        }

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            setupTabs(tab, position)
        }.attach()

        main_activity_play_image_view.setOnClickListener(this)
        tabLayout.addOnTabSelectedListener(this)

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
            3 -> {
                tab.setIcon(if (tab.isSelected) R.drawable.ic_people_green_24dp else R.drawable.ic_people_white_24dp)
            }
            4 -> {
                tab.setIcon(if (tab.isSelected) R.drawable.ic_settings_green_24dp else R.drawable.ic_settings_white_24dp)
            }
        }
    }

}
