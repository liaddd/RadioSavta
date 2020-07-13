package com.liad.radiosavta.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.climacell.statefulLiveData.core.StatefulData
import com.liad.radiosavta.R
import com.liad.radiosavta.adapters.ProgramsAdapter
import com.liad.radiosavta.models.Program
import com.liad.radiosavta.utils.Constants
import com.liad.radiosavta.utils.Constants.PROGRAM_ID
import com.liad.radiosavta.utils.extension.changeFragment
import com.liad.radiosavta.utils.extension.show
import com.liad.radiosavta.viewmodels.ProgramsViewModel
import kotlinx.android.synthetic.main.fragment_programs.*
import org.koin.android.ext.android.inject

class ProgramsFragment : Fragment() {

    companion object {
        fun newInstance(): ProgramsFragment = ProgramsFragment()
    }

    private val programAdapter =
        ProgramsAdapter().apply { listener = onProgramClickedListener() }

    private val programsViewModel: ProgramsViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_programs, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setObservers()
    }

    private fun initViews() {
        programs_fragment_recycler_view.apply {
            adapter = programAdapter
            activity?.let {
                layoutManager =
                    GridLayoutManager(it, Constants.SPAN_COUNT, RecyclerView.VERTICAL, false)
            }
        }

    }

    private fun setObservers() {
        programsViewModel.getPrograms().observe(viewLifecycleOwner, Observer {
            when (it) {
                is StatefulData.Success -> {
                    programs_fragment_progress_bar?.show(false)
                    programAdapter.setPrograms(it.data)
                }
                is StatefulData.Loading -> programs_fragment_progress_bar?.show()
                is StatefulData.Error -> {}
            }
        })
    }

    private fun onProgramClickedListener(): ProgramsAdapter.IProgramListener? {
        return object : ProgramsAdapter.IProgramListener {
            override fun onClick(program: Program) {
                openProgramDetailFragment(program.id)
            }
        }
    }

    fun openProgramDetailFragment(programId: Int?) {
        val programDetailsFragment = ProgramDetailsFragment.newInstance()
        if (programDetailsFragment.programId == programId) {
            return
        }
        parentFragment?.let {
            val bundle = Bundle()
            bundle.putInt(PROGRAM_ID, programId ?: 0)
            programDetailsFragment.arguments = bundle
            changeFragment(
                it.childFragmentManager,
                R.id.inner_fragment_frame_layout,
                programDetailsFragment,
                true
            )
        }
    }
}
