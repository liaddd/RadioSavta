package com.liad.radiosavta.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.liad.radiosavta.R
import com.liad.radiosavta.utils.extension.changeFragment

class InnerContainerFragment : Fragment() {

    companion object {
        fun newInstance(bundle: Bundle? = null): InnerContainerFragment {
            val innerContainerFragment = InnerContainerFragment()
            if (bundle != null) innerContainerFragment.arguments = bundle
            return innerContainerFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_inner_container, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {
            changeFragment(
                /*it.supportFragmentManager*/childFragmentManager,
                R.id.inner_fragment_frame_layout,
                ProgramsFragment.newInstance()
            )
        }
    }

}
