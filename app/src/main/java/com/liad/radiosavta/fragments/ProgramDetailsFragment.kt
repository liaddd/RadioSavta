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
import com.liad.radiosavta.models.User
import com.liad.radiosavta.utils.Constants
import com.liad.radiosavta.utils.extension.convertIntToDay
import com.liad.radiosavta.utils.extension.removeSeconds
import com.liad.radiosavta.utils.extension.show
import com.liad.radiosavta.viewmodels.ProgramsViewModel
import kotlinx.android.synthetic.main.fragment_program_details.*
import org.koin.android.ext.android.inject

class ProgramDetailsFragment : Fragment() {

    companion object {
        fun newInstance(): ProgramDetailsFragment = ProgramDetailsFragment()
    }

    private lateinit var presentedByRV: RecyclerView
    private lateinit var recordedShowRV: RecyclerView
    private val programsViewModel: ProgramsViewModel by inject()

    private val presentedByAdapter = PresentedByAdapter().apply { listener = getIUserClickedListener() }
    private val recordedShowsAdapter = RecordedShowsAdapter()

    var programId: Int? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = inflater.inflate(R.layout.fragment_program_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        setObservers()
    }

    private fun initViews() {
        activity?.let {
            presentedByRV = program_details_fragment_presented_recycler_view.apply {
                adapter = presentedByAdapter
                layoutManager = LinearLayoutManager(it, RecyclerView.HORIZONTAL, false)
            }

            recordedShowRV = program_details_fragment_recorded_shows_recycler_view.apply {
                adapter = recordedShowsAdapter
                layoutManager = LinearLayoutManager(it, RecyclerView.VERTICAL, false)
            }
        }
    }

    private fun setObservers() {
        programId = arguments?.getInt(Constants.PROGRAM_ID)

        programId?.let { id ->
            programsViewModel.getProgramsById(id).observe(viewLifecycleOwner, Observer {
                when (it) {
                    is StatefulData.Success -> {
                        program_details_fragment_progress_bar?.show(false)
                        populateFields(it.data)
                    }
                    is StatefulData.Loading -> program_details_fragment_progress_bar?.show()
                    is StatefulData.Error -> {
                    }
                }
            })
        }
    }

    private fun populateFields(program: Program) {
        program_details_fragment_title_text.text = program.nameEn
        program_details_fragment_secondary_title.text = program.description
        activity?.let {
            Glide.with(it)
                .load(program.getCover() ?: program.users?.let { users -> users[0].getProfileImg() })
                .into(program_details_fragment_image_view)
        }

        val startTime = program.programTimes?.startTime?.removeSeconds()
        val endTime = program.programTimes?.endTime?.removeSeconds()

        program_details_fragment_hour_text_view.text = getString(
            R.string.hours,
            startTime,
            endTime
        )
        program.users?.let { presentedByAdapter.setUsers(it) }
        program_details_fragment_time_text_view.text =
            convertIntToDay(program.programTimes?.dayOfWeek ?: 1)

        program.recorded_shows?.let {
            if (it.isNullOrEmpty()) {
                program_details_fragment_no_recorded_show_text?.show()
            } else {
                program_details_fragment_no_recorded_show_text?.show(false)
                recordedShowRV.show()
                recordedShowsAdapter.setRecordedShow(it)

            }
        }
    }

    private fun getIUserClickedListener(): PresentedByAdapter.IUserClickedListener =
        object : PresentedByAdapter.IUserClickedListener {
            override fun onClick(user: User, view: View) {
                // todo Liad - add flip animation to users click's
            }
        }

}

