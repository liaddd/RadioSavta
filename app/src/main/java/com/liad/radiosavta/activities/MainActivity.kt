package com.liad.radiosavta.activities

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.viewpager2.widget.ViewPager2
import co.climacell.statefulLiveData.core.StatefulData
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.analytics.Tracker
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.liad.radiosavta.R
import com.liad.radiosavta.RadioSavtaApplication
import com.liad.radiosavta.adapters.FragmentPagerAdapter
import com.liad.radiosavta.services.PlayMusicService
import com.liad.radiosavta.utils.Constants
import com.liad.radiosavta.utils.Constants.LOCAL_BROADCAST_UPDATE
import com.liad.radiosavta.utils.extension.sendEvent
import com.liad.radiosavta.viewmodels.ProgramsViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.banner.*
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity(), TabLayout.OnTabSelectedListener, View.OnClickListener {

    private lateinit var customTabView: View
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var fragmentPagerAdapter: FragmentPagerAdapter
    private lateinit var localBroadcastManager: LocalBroadcastManager

    private var mTracker : Tracker? = null

    private val myBroadcast : BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            updatePlayPauseButtonState(intent?.extras?.getBoolean(Constants.IS_PLAYING) ?: false)
        }
    }

    private val programsViewModel: ProgramsViewModel by inject()

    // TODO Liad - refactor all strange lists

    private val unselectedTabsIcons = listOf(
        R.drawable.ic_home_white_24dp,
        R.drawable.ic_music_note_white_24dp,
        0,
        R.drawable.ic_people_white_24dp,
        R.drawable.charity_icon_white_small
    )

    private val selectedTabIcons = listOf(
        R.drawable.ic_home_green_24dp,
        R.drawable.ic_music_note_green_24dp,
        0,
        R.drawable.ic_people_green_24dp,
        R.drawable.ic_charity_icon_selected
    )

    private val selectedTabText = listOf(
        R.string.home,
        R.string.programs,
        R.string.empty_string,
        R.string.broadcasters,
        R.string.donate
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setFullScreen()
        setContentView(R.layout.activity_main)

        // initialize MobileAds to whole app
        MobileAds.initialize(this)
        initInterstitialAd()
        initAdView()
        initViews()

        localBroadcastManager.registerReceiver(myBroadcast , IntentFilter(LOCAL_BROADCAST_UPDATE))
    }

    private fun initInterstitialAd() {
        val interstitialAd = InterstitialAd(this)
        interstitialAd.adUnitId = getString(R.string.ad_view_prod_interstitial_unit_id/*R.string.ad_view_test_interstitial_unit_id*/)
        interstitialAd.loadAd(AdRequest.Builder().build())
        interstitialAd.adListener = object : AdListener() {
            override fun onAdLoaded() {
                if (interstitialAd.isLoaded) interstitialAd.show()
            }
        }
    }

    private fun initAdView() {
        val adView = banner_adView
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }

    private fun initViews() {
        mTracker = RadioSavtaApplication.sTracker

        localBroadcastManager = LocalBroadcastManager.getInstance(this)
        fragmentPagerAdapter = FragmentPagerAdapter(this)

        main_activity_play_image_view.setOnClickListener(this)

        viewPager = main_activity_view_pager.apply {
            adapter = fragmentPagerAdapter
            isUserInputEnabled = false
            offscreenPageLimit = 2
        }

        initTabLayout()

    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        tab?.let { handleTabState(it) }
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
        tab?.let {
            if (it.position == 1) {
                popAllChildStack()
            }
        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
        tab?.let { handleTabState(it) }
    }

    private fun popAllChildStack() {
        for (fragment in supportFragmentManager.fragments) {
            if (fragment.isVisible) {
                val childFragManager = fragment.childFragmentManager
                if (childFragManager.backStackEntryCount > 0) {
                    for (i in childFragManager.backStackEntryCount downTo 0) {
                        childFragManager.popBackStack()
                    }
                    childFragManager.popBackStack()
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.main_activity_play_image_view -> {
                onPlayPauseClicked()
            }
        }
    }

    private fun initTabLayout() {
        tabLayout = main_activity_tab_layout

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            setupTabs(tab, position)
        }.attach()

        tabLayout.addOnTabSelectedListener(this)

        val tabStrip = (tabLayout.getChildAt(0) as LinearLayout)

        tabStrip.getChildAt(2).setOnTouchListener { _, _ ->
            onPlayPauseClicked()
            true
        }
    }

    private fun onPlayPauseClicked() {
        val currentSongName = (programsViewModel.getCurrentPlayingSongTitle().value as? StatefulData.Success)?.data
        RadioSavtaApplication.mediaPlayer?.let {
            mTracker?.sendEvent(action = if(it.isPlaying) "Pause" else "Play")
            startService(currentSongName)
            main_activity_play_image_view.setImageResource(if (it.isPlaying) R.drawable.play_button_background else R.drawable.pause_button_background)
        }
    }

    private fun updatePlayPauseButtonState(isPlaying : Boolean) {
        main_activity_play_image_view.setImageResource(if (isPlaying) R.drawable.pause_button_background else R.drawable.play_button_background)
    }

    override fun onResume() {
        super.onResume()
        //mTracker?.sendScreenName(this::class.java.simpleName)
        RadioSavtaApplication.mediaPlayer?.let {
            main_activity_play_image_view?.setImageResource(if (it.isPlaying) R.drawable.pause_button_background else R.drawable.play_button_background)
        }
    }

    // todo Liad - refactor function
    private fun setupTabs(tab: TabLayout.Tab, position: Int) {
        customTabView = layoutInflater.inflate(R.layout.custom_tab_item, null)
        val tabIcon = customTabView.findViewById<ImageView>(R.id.custom_tab_item_image_view)
        val tabText = customTabView.findViewById<TextView>(R.id.custom_tab_item_text_view)

        if (position == 0) {
            tabText.setTextColor(resources.getColor(R.color.green, null))
            tabIcon.setImageResource(selectedTabIcons[position])
        } else tabIcon.setImageResource(unselectedTabsIcons[position])

        tabText.text = getString(selectedTabText[position])
        tab.customView = customTabView
    }

    // todo Liad - refactor function
    private fun handleTabState(tab: TabLayout.Tab) {
        customTabView = layoutInflater.inflate(R.layout.custom_tab_item, null)
        val tabText = customTabView.findViewById<TextView>(R.id.custom_tab_item_text_view)
        val tabIcon = customTabView.findViewById<ImageView>(R.id.custom_tab_item_image_view)
        tab.customView = null
        if (tab.isSelected) {
            tabIcon.setImageResource(selectedTabIcons[tab.position])
            tabText.setTextColor(resources.getColor(R.color.green, null))
        } else {
            tabIcon.setImageResource(unselectedTabsIcons[tab.position])
            tabText.setTextColor(resources.getColor(android.R.color.white, null))
        }
        tabText.text = getString(selectedTabText[tab.position])
        tab.customView = customTabView
    }

    private fun handleInnerFragmentBackStack(): Boolean {
        var isPopped = false
        for (fragment in supportFragmentManager.fragments) {
            if (fragment.isVisible) {
                val childFragManager = fragment.childFragmentManager
                if (childFragManager.backStackEntryCount > 0) {
                    childFragManager.popBackStack()
                    isPopped = true
                }
            }
        }
        return isPopped
    }

    private fun goToMainPage() = tabLayout.getTabAt(0)?.select()

    private fun startService(currentSong: String? = "") {
        RadioSavtaApplication.mediaPlayer?.let {
            val intent = Intent(this, PlayMusicService::class.java)
            intent.putExtra(Constants.SONG_NAME, currentSong)
            ContextCompat.startForegroundService(this, intent)
        }
    }

    override fun onBackPressed() {
        if (handleInnerFragmentBackStack()) return
        if (viewPager.currentItem > 0) {
            goToMainPage()
            return
        }
        super.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
        localBroadcastManager.unregisterReceiver(myBroadcast)
    }
}


