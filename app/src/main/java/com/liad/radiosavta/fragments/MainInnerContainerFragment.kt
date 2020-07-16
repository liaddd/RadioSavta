package com.liad.radiosavta.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.liad.radiosavta.R
import com.liad.radiosavta.utils.extension.changeFragment

class MainInnerContainerFragment : Fragment() {

    companion object {
        fun newInstance(): MainInnerContainerFragment = MainInnerContainerFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_main_inner_container, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {
            changeFragment(childFragmentManager, R.id.main_inner_fragment_frame_layout, MainFragment.newInstance())
        }
    }

}
