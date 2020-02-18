package com.liad.radiosavta.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.liad.radiosavta.fragments.*
import com.liad.radiosavta.utils.Constants.NUM_PAGE


class MainFragmentAdapter(fragmentActivity : FragmentActivity) : FragmentStateAdapter(fragmentActivity){

    override fun getItemCount(): Int {
        return NUM_PAGE
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> MainFragment.newInstance()
            1 -> InnerContainerFragment.newInstance()
            2 -> PlayFragment.newInstance()
            3 -> FriendsFragment.newInstance()
            4 -> SettingsFragment.newInstance()
            else -> TODO("couldn't find Fragment!")
        }
    }
}