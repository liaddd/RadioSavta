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
import com.liad.radiosavta.adapters.UsersAdapter
import com.liad.radiosavta.models.User
import com.liad.radiosavta.utils.Constants.SPAN_COUNT
import com.liad.radiosavta.viewmodels.ProgramsViewModel
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

        initViews()
        setObservers()
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
                is StatefulData.Success -> {
                    setUsers(it.data)
                }
                is StatefulData.Loading -> {
                }
                is StatefulData.Error -> {
                }
            }
        })
    }

    private fun setUsers(users: List<User>) {
        usersAdapter.setUsers(users)
    }
}
