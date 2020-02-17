package com.liad.radiosavta.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.liad.radiosavta.R
import com.liad.radiosavta.adapters.PresentedByAdapter
import com.liad.radiosavta.adapters.RecordedShowsAdapter
import kotlinx.android.synthetic.main.fragment_music.*


class MusicFragment : Fragment() {

    companion object {
        fun newInstance(): MusicFragment {
            return MusicFragment()
        }
    }

    private lateinit var presentedByRV: RecyclerView
    private lateinit var recordedShowRV: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_music, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }


    private fun initViews() {
        activity?.let {
            presentedByRV = music_fragment_presented_recycler_view.apply {
                adapter = PresentedByAdapter()
                layoutManager = LinearLayoutManager(it, RecyclerView.HORIZONTAL, false)
            }

            recordedShowRV = music_fragment_recorded_shows_recycler_view.apply {
                adapter = RecordedShowsAdapter()
                layoutManager = LinearLayoutManager(it, RecyclerView.VERTICAL, false)
            }
        }
    }


}
