package com.liad.radiosavta.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.liad.radiosavta.fragments.*


class MainFragmentAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    private val fragments = listOf(
        MainFragment.newInstance(),
        InnerContainerFragment.newInstance(),
        PlayFragment.newInstance(),
        UsersFragment.newInstance(),
        SettingsFragment.newInstance()
    )

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment = fragments[position]

}