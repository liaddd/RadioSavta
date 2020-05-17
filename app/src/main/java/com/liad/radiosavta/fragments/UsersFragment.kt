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
import com.google.android.gms.ads.AdRequest
import com.liad.radiosavta.R
import com.liad.radiosavta.adapters.UsersAdapter
import com.liad.radiosavta.utils.Constants.SPAN_COUNT
import com.liad.radiosavta.viewmodels.ProgramsViewModel
import kotlinx.android.synthetic.main.banner.*
import kotlinx.android.synthetic.main.fragment_users.*
import org.koin.android.ext.android.inject

class UsersFragment : Fragment() {

    companion object {
        fun newInstance(): UsersFragment = UsersFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_users, container, false)

    private val usersAdapter = UsersAdapter()
    private val programsViewModel: ProgramsViewModel by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdView()
        initViews()
        setObservers()
    }

    private fun initAdView() {
        val adView = banner_adView
        val adRequest = AdRequest.Builder().build()
        adView?.loadAd(adRequest)
    }

    private fun initViews() {
        fragment_users_recycler_view.apply {
            adapter = usersAdapter
            activity?.let {
                layoutManager = GridLayoutManager(it, SPAN_COUNT, RecyclerView.VERTICAL, false)
            }
        }
    }

    private fun setObservers() {
        programsViewModel.getUsers().observe(viewLifecycleOwner, Observer {
            when (it) {
                is StatefulData.Success -> usersAdapter.setUsers(it.data)
                is StatefulData.Loading -> {
                }
                is StatefulData.Error -> {
                }
            }
        })
    }
}
