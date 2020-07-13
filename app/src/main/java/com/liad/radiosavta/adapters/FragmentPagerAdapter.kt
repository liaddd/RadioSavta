package com.liad.radiosavta.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.liad.radiosavta.fragments.*
import com.liad.radiosavta.models.Program
import kotlinx.android.synthetic.main.activity_main.*

class FragmentPagerAdapter(private var fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    private val fragments = mutableListOf(
        MainFragment.newInstance().apply { listener = getProgramClickedListener() },
        InnerContainerFragment.newInstance(),
        PlayFragment.newInstance(),
        UsersFragment.newInstance(),
        DonationFragment.newInstance()
    )

    private fun getProgramClickedListener(): MainFragment.IOnProgramClickedListener {
        return object : MainFragment.IOnProgramClickedListener {
            override fun onProgramClicked(program: Program) {
                fragmentActivity.main_activity_tab_layout?.getTabAt(1)?.select()
                (fragments[1] as? InnerContainerFragment)?.openProgramDetails(program.id ?: 0)
            }
        }
    }

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]

}