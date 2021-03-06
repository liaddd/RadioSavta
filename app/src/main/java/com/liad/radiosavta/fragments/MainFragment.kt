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
import com.liad.radiosavta.utils.Constants
import com.liad.radiosavta.utils.extension.changeFragment
import com.liad.radiosavta.utils.extension.show
import com.liad.radiosavta.viewmodels.ProgramsViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.android.ext.android.inject

class MainFragment : Fragment() {

    companion object {
        fun newInstance(): MainFragment = MainFragment()
    }

    private val programsAdapter = ProgramsAdapter().apply { listener = getAdapterListener() }
    private val programsViewModel: ProgramsViewModel by inject()
    //var listener: IOnProgramClickedListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = inflater.inflate(R.layout.fragment_main, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        setObservers()
    }

    private fun initViews() {
        main_fragment_recycler_view?.apply {
            adapter = programsAdapter
            layoutManager = activity?.let { LinearLayoutManager(it, RecyclerView.HORIZONTAL, false) }
        }
    }

    private fun setObservers() {
        programsViewModel.getPrograms().observe(viewLifecycleOwner, Observer {
            when (it) {
                is StatefulData.Success -> programsAdapter.setPrograms(it.data)
                is StatefulData.Loading -> main_fragment_progress_bar?.show()
                is StatefulData.Error -> main_fragment_progress_bar?.show(false)
            }
        })

        programsViewModel.getCurrentPlayingSongTitle().observeForever {
            when (it) {
                is StatefulData.Success -> {
                    main_fragment_progress_bar?.show(false)
                    main_fragment_song_name.text = it.data
                }
                is StatefulData.Loading -> main_fragment_progress_bar?.show()
                is StatefulData.Error -> main_fragment_progress_bar?.show(false)
            }
        }
    }

    private fun getAdapterListener(): ProgramsAdapter.IProgramListener? =
        object : ProgramsAdapter.IProgramListener {
            override fun onClick(program: Program) {
                //listener?.onProgramClicked(program)
                val programDetailsFragment = ProgramDetailsFragment.newInstance()
                parentFragment?.let {
                    val bundle = Bundle()
                    bundle.putInt(Constants.PROGRAM_ID, program.id ?: 0)
                    programDetailsFragment.arguments = bundle
                    changeFragment(
                        it.childFragmentManager,
                        R.id.main_inner_fragment_frame_layout,
                        programDetailsFragment,
                        true
                    )
                }
            }
        }

    /*interface IOnProgramClickedListener {
        fun onProgramClicked(program: Program)
    }*/
}
