package com.liad.radiosavta.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import co.climacell.statefulLiveData.core.StatefulData
import com.liad.radiosavta.R
import com.liad.radiosavta.viewmodels.ProgramsViewModel
import kotlinx.android.synthetic.main.fragment_play.*
import org.koin.android.ext.android.inject

class PlayFragment : Fragment() {


    companion object {
        fun newInstance(): PlayFragment {
            return PlayFragment()
        }
    }

    private val programViewModel : ProgramsViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_play, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        setObservers()
    }


    private fun initViews() {

    }

    private fun setObservers() {
        programViewModel.getCurrentPlayingSongTitle().observe(viewLifecycleOwner , Observer {
            when(it){
                is StatefulData.Success -> {
                    changeText(it.data)
                }
                is StatefulData.Loading -> {}
                is StatefulData.Error -> {}
            }
        })
    }

    private fun changeText(newText: String) {
        play_fragment_text_view.text = newText
    }

}
