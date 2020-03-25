package com.liad.radiosavta.viewmodels

import androidx.lifecycle.ViewModel
import com.liad.radiosavta.repositories.RadioRepository

class ProgramsViewModel(private val radioSavtaRepository: RadioRepository) : ViewModel() {


    fun getPrograms() = radioSavtaRepository.statefulLiveDataPrograms

    fun getProgramsById(id: Int) = radioSavtaRepository.getProgramById(id)

    fun getCurrentPlayingSongTitle() = radioSavtaRepository.statefulLiveDataCurrentSong

    fun getUsers() = radioSavtaRepository.statefulLiveDataUsers
}