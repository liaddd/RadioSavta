package com.liad.radiosavta.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.climacell.statefulLiveData.core.StatefulData
import com.liad.radiosavta.R
import com.liad.radiosavta.adapters.ProgramsAdapter
import com.liad.radiosavta.models.Program
import com.liad.radiosavta.viewmodels.ProgramsViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.android.ext.android.inject

class MainFragment : Fragment() {

    companion object {
        fun newInstance(): MainFragment = MainFragment()
    }

    private val programsAdapter = ProgramsAdapter().apply { listener = getAdapterListener() }
    private val programsViewModel: ProgramsViewModel by inject()
    var listener: IOnProgramClickedListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_main, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        setObservers()
    }

    private fun initViews() {
        main_fragment_recycler_view.apply {
            adapter = programsAdapter
            layoutManager =
                activity?.let { LinearLayoutManager(it, RecyclerView.HORIZONTAL, false) }
        }
    }

    private fun setObservers() {
        programsViewModel.getPrograms().observe(viewLifecycleOwner, Observer {
            when (it) {
                is StatefulData.Success -> {
                    programsAdapter.setPrograms(it.data.shuffled())
                }
                is StatefulData.Loading -> {
                    showProgress()
                }
                is StatefulData.Error -> {
                    showProgress(false)
                }
            }
        })

        programsViewModel.getCurrentPlayingSongTitle().observe(viewLifecycleOwner, Observer {
            when (it) {
                is StatefulData.Success -> {
                    showProgress(false)
                    main_fragment_song_name.text = it.data
                }
                is StatefulData.Loading -> {
                    showProgress()
                }
                is StatefulData.Error -> {
                    showProgress(false)
                }
            }
        })
    }

    private fun showProgress(show: Boolean = true) {
        main_fragment_progress_bar.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun getAdapterListener(): ProgramsAdapter.IProgramListener? =
        object : ProgramsAdapter.IProgramListener {
            override fun onClick(program: Program) {
                // todo Liad - handle program click (navigate to second tab and move to child fragment - ProgramDetailsFragment)
                listener?.onProgramClicked(program)
            }
        }


    interface IOnProgramClickedListener {
        fun onProgramClicked(program: Program)
    }
}
