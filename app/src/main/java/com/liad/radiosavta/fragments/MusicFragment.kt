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
import com.bumptech.glide.Glide
import com.liad.radiosavta.R
import com.liad.radiosavta.adapters.PresentedByAdapter
import com.liad.radiosavta.adapters.RecordedShowsAdapter
import com.liad.radiosavta.models.Program
import com.liad.radiosavta.utils.Constants
import com.liad.radiosavta.utils.extension.convertIntToDay
import com.liad.radiosavta.viewmodels.ProgramsViewModel
import kotlinx.android.synthetic.main.fragment_music.*
import org.koin.android.ext.android.inject


class MusicFragment : Fragment() {

    companion object {
        fun newInstance(bundle: Bundle? = null): MusicFragment {
            val musicFragment = MusicFragment()
            if (bundle != null) musicFragment.arguments = bundle
            return musicFragment
        }
    }

    private lateinit var presentedByRV: RecyclerView
    private lateinit var recordedShowRV: RecyclerView
    private val programsViewModel: ProgramsViewModel by inject()
    private val presentedByAdapter = PresentedByAdapter()
    private val recordedShowsAdapter = RecordedShowsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_music, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        setObservers()
    }

    private fun setObservers() {
        val programId = arguments?.getInt(Constants.PROGRAM_ID)
        programId?.let { id ->
            programsViewModel.getProgramsById(id).observe(viewLifecycleOwner, Observer {
                when (it) {
                    is StatefulData.Success -> {
                        showProgress(false)
                        populateFields(it.data)
                    }
                    is StatefulData.Loading -> {
                        showProgress()
                    }
                    is StatefulData.Error -> {
                    }
                }
            })
        }

    }

    private fun populateFields(program: Program) {
        music_fragment_title_text.text = program.nameEn
        music_fragment_secondary_title.text = program.description
        activity?.let {
            Glide.with(it)
                .load(program.getCover())
                .into(music_fragment_image_view)
        }
        music_fragment_hour_text_view.text =
            "${program.programTimes?.startTime} - ${program.programTimes?.endTime}"
        program.users?.let { presentedByAdapter.setUsers(it) }
        music_fragment_time_text_view.text = convertIntToDay(program.programTimes?.dayOfWeek ?: 1)
    }

    private fun showProgress(show: Boolean = true) {
        music_fragment_progress_bar.visibility = if (show) View.VISIBLE else View.GONE
    }


    private fun initViews() {
        activity?.let {
            presentedByRV = music_fragment_presented_recycler_view.apply {
                adapter = presentedByAdapter
                layoutManager = LinearLayoutManager(it, RecyclerView.HORIZONTAL, false)
            }

            recordedShowRV = music_fragment_recorded_shows_recycler_view.apply {
                adapter = recordedShowsAdapter
                layoutManager = LinearLayoutManager(it, RecyclerView.VERTICAL, false)
            }
        }
    }


}

